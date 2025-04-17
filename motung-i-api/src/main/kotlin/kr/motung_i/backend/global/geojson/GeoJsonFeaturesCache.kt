package kr.motung_i.backend.global.geojson

import jakarta.annotation.PostConstruct
import kr.motung_i.backend.global.geojson.dto.GeoJsonFeatures
import kr.motung_i.backend.global.geojson.enums.Country
import org.springframework.core.io.support.PathMatchingResourcePatternResolver
import org.springframework.stereotype.Component

@Component
class GeoJsonFeaturesCache {
    private lateinit var geoJsonFeatures: List<GeoJsonFeatures>

    @PostConstruct
    private fun init() {
        val resolver = PathMatchingResourcePatternResolver()
        val resources = resolver.getResources("classpath:geojson/**/*.geojson")

        geoJsonFeatures = resources.map { GeoJsonFeatures.toDto(it) }
    }

    fun findCountryGeoJsonFeatureByCountry(country: Country): GeoJsonFeatures? =
        geoJsonFeatures.firstOrNull { it.country == country.name }
}