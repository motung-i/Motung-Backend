package kr.motung_i.backend.global.geojson

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import kr.motung_i.backend.global.exception.CustomException
import kr.motung_i.backend.global.exception.enums.CustomErrorCode
import kr.motung_i.backend.global.geojson.dto.District
import kr.motung_i.backend.global.geojson.dto.Local
import kr.motung_i.backend.global.geojson.dto.Neighborhood
import kr.motung_i.backend.global.geojson.dto.Region
import kr.motung_i.backend.persistence.tour.entity.Country
import kr.motung_i.backend.global.geojson.formatter.LocalFormatterService
import org.geolatte.geom.*
import org.geolatte.geom.crs.CoordinateReferenceSystems
import org.geolatte.geom.json.GeolatteGeomModule
import org.springframework.core.io.Resource
import org.springframework.stereotype.Component

@Component
class LocalsFactory(
    private val localFormatterService: LocalFormatterService,
) {
    fun toDto(resource: Resource): Local {
        val objectMapper = ObjectMapper()
            .registerModules(GeolatteGeomModule())

        val inputStream = Country::class.java.classLoader
            .getResourceAsStream("geojson/${resource.filename}")
            ?: throw CustomException(CustomErrorCode.NOT_FOUND_GEOJSON)

        val json = inputStream.bufferedReader().use { it.readText() }
        val root = objectMapper.readTree(json)

        val country = Country.valueOf(resource.filename!!.substringBefore(".geojson"))
        val features = root["features"]
        val regions = parseRegions(features, country)

        return Local(
            country = country,
            regions = regions,
        )
    }

    fun parseRegions(features: JsonNode, country: Country): List<Region> =
        features.groupBy { it["properties"]["sidonm"].asText() }.map { (regionName, regionFeatures) ->
            val regionAlias = localFormatterService.formatToRegionAlias(regionName, country)

            val districts = regionFeatures.groupBy { it["properties"]["sggnm"].asText() }
                .map { (districtName, districtFeatures) ->
                    val districtAlias = localFormatterService.formatToDistrictAlias(districtName, country)

                    val neighborhoods = districtFeatures.map { features ->
                        val geometry = parseMultiPolygon(features["geometry"]["coordinates"])
                        val neighborhoodName = features["properties"]["adm_nm"].asText().split(" ").last()
                        Neighborhood(neighborhoodName, geometry)
                    }

                    District(districtName, districtAlias, neighborhoods, regionName)
                }

            Region(regionName, regionAlias, districts)
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