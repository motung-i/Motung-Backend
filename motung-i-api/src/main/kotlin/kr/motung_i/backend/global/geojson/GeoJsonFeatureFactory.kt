package kr.motung_i.backend.global.geojson

import com.fasterxml.jackson.databind.JsonNode
import kr.motung_i.backend.global.geojson.formatter.LocalFormatterService
import kr.motung_i.backend.global.geojson.dto.GeoJsonFeature
import kr.motung_i.backend.global.geojson.dto.local.District
import kr.motung_i.backend.global.geojson.dto.local.Local
import kr.motung_i.backend.global.geojson.dto.local.Neighborhood
import kr.motung_i.backend.global.geojson.dto.local.Region
import kr.motung_i.backend.global.geojson.enums.Country
import org.geolatte.geom.*
import org.geolatte.geom.crs.CoordinateReferenceSystems
import org.springframework.stereotype.Component

@Component
class GeoJsonFeatureFactory(
    private val localFormatterService: LocalFormatterService,
) {
    fun toDto(feature: JsonNode, country: Country): GeoJsonFeature =
        feature.let {
            val neighborhoodName = feature["properties"]["adm_nm"].asText().split(" ").last()
            val districtName = feature["properties"]["sggnm"].asText()
            val regionName = feature["properties"]["sidonm"].asText()
            val localName = feature["properties"]["adm_nm"].asText()

            val districtAlias = localFormatterService.formatToDistrictAlias(districtName, country)
            val regionAlias = localFormatterService.formatToRegionAlias(regionName, country)

            val neighborhood = Neighborhood(neighborhoodName)
            val district = District(districtName, districtAlias, neighborhood)
            val region = Region(regionName, regionAlias, district)
            val local = Local(localName, region)

            GeoJsonFeature(
                geometry = parseMultiPolygon(it["geometry"]["coordinates"]),
                local = local
            )
        }

    private fun parseMultiPolygon(coordinates: JsonNode): MultiPolygon<G2D> {
        val crs = CoordinateReferenceSystems.WGS84
        val polygons = mutableListOf<List<LinearRing<G2D>>>()

        coordinates.map { coordinate ->
            val rings = mutableListOf<LinearRing<G2D>>()

            val positionBuilder = PositionSequenceBuilders.variableSized(G2D::class.java)
            coordinate.map { node ->
                node.map {
                    val lon = it[0].asDouble()
                    val lat = it[1].asDouble()
                    val position = Positions.mkPosition(crs, lon, lat)
                    positionBuilder.add(position)
                }

                val positionSequence = positionBuilder.toPositionSequence()
                rings.add(Geometries.mkLinearRing(positionSequence, crs))
            }

            polygons.add(rings)
        }

        return Geometries.mkMultiPolygon(polygons.map { Geometries.mkPolygon(it) })
    }
}