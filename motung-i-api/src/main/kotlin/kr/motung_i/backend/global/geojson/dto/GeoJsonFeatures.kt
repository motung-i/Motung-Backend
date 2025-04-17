package kr.motung_i.backend.global.geojson.dto

import com.fasterxml.jackson.databind.ObjectMapper
import kr.motung_i.backend.global.exception.CustomException
import kr.motung_i.backend.global.exception.enums.CustomErrorCode
import org.geolatte.geom.json.GeolatteGeomModule
import org.springframework.core.io.Resource

data class GeoJsonFeatures(
    val country: String,
    val regionSet: Set<String>,
    val districtSet: Set<String>,
    val geoJsonFeatures: List<GeoJsonFeature>,
) {
    companion object {
        fun toDto(resource: Resource): GeoJsonFeatures {
            val objectMapper = ObjectMapper()
                .registerModules(GeolatteGeomModule())

            val inputStream = GeoJsonFeatures::class.java.classLoader
                .getResourceAsStream("geojson/${resource.filename}")
                ?: throw CustomException(CustomErrorCode.NOT_FOUND_GEOJSON)

            val json = inputStream.bufferedReader().use { it.readText() }
            val root = objectMapper.readTree(json)

            val features = root["features"]
            val geoJsonFeatures = features.map { GeoJsonFeature.toDto(it) }

            return GeoJsonFeatures(
                country = resource.filename!!.substringBefore(".geojson"),
                geoJsonFeatures = geoJsonFeatures,
                regionSet = geoJsonFeatures.map { it.region }.toSet(),
                districtSet = geoJsonFeatures.map { it.district }.toSet(),
            )
        }
    }
}