package kr.motung_i.backend.global.geojson

import com.fasterxml.jackson.databind.ObjectMapper
import kr.motung_i.backend.global.exception.CustomException
import kr.motung_i.backend.global.exception.enums.CustomErrorCode
import kr.motung_i.backend.global.geojson.dto.GeoJsonFeature
import kr.motung_i.backend.global.geojson.dto.GeoJsonFeatures
import kr.motung_i.backend.global.geojson.enums.Country
import org.geolatte.geom.json.GeolatteGeomModule
import org.springframework.core.io.Resource
import org.springframework.stereotype.Component

@Component
class GeoJsonFeaturesFactory(
    private val geoJsonFeatureFactory: GeoJsonFeatureFactory,
) {
    fun toDto(resource: Resource): GeoJsonFeatures {
        val objectMapper = ObjectMapper()
            .registerModules(GeolatteGeomModule())

        val inputStream = GeoJsonFeatures::class.java.classLoader
            .getResourceAsStream("geojson/${resource.filename}")
            ?: throw CustomException(CustomErrorCode.NOT_FOUND_GEOJSON)

        val json = inputStream.bufferedReader().use { it.readText() }
        val root = objectMapper.readTree(json)

        val country = Country.valueOf(resource.filename!!.substringBefore(".geojson"))
        val features = root["features"]
        val geoJsonFeatures = features.map { geoJsonFeatureFactory.toDto(it, country) }

        return GeoJsonFeatures(
            country = country,
            geoJsonFeatures = geoJsonFeatures,
        )
    }
}