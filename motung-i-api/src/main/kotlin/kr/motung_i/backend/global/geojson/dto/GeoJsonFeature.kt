package kr.motung_i.backend.global.geojson.dto

import com.fasterxml.jackson.databind.JsonNode
import org.geolatte.geom.G2D
import org.geolatte.geom.Geometries
import org.geolatte.geom.LinearRing
import org.geolatte.geom.MultiPolygon
import org.geolatte.geom.PositionSequenceBuilders
import org.geolatte.geom.Positions
import org.geolatte.geom.crs.CoordinateReferenceSystems

data class GeoJsonFeature(
    val local: String,
    val region: String,
    val district: String,
    val neighborhood: String,
    val geometry: MultiPolygon<G2D>,
) {
    companion object {
        fun toDto(feature: JsonNode): GeoJsonFeature =
            feature.let {
                GeoJsonFeature(
                    local = it["properties"]["adm_nm"].asText(),
                    region = it["properties"]["sidonm"].asText(),
                    district = it["properties"]["sggnm"].asText(),
                    neighborhood = it["properties"]["adm_nm"].asText().split(" ").last(),
                    geometry = parseMultiPolygon(it["geometry"]["coordinates"])
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
}