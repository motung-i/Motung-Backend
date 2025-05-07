package kr.motung_i.backend.global.geojson

import jakarta.annotation.PostConstruct
import kr.motung_i.backend.global.geojson.dto.GeoLocal
import kr.motung_i.backend.persistence.tour_location.entity.Country
import org.springframework.core.io.support.PathMatchingResourcePatternResolver
import org.springframework.stereotype.Component

@Component
class LocalsCache(
    private val localsFactory: LocalsFactory,
) {
    private lateinit var geoLocals: List<GeoLocal>

    @PostConstruct
    private fun init() {
        val resolver = PathMatchingResourcePatternResolver()
        val resources = resolver.getResources("classpath:geojson/**/*.geojson")

        geoLocals = resources.map { localsFactory.toDto(it) }
    }

    fun findLocalByCountry(country: Country): GeoLocal? =
        geoLocals.firstOrNull { it.country == country }
}