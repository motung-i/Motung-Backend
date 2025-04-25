package kr.motung_i.backend.global.geojson

import jakarta.annotation.PostConstruct
import kr.motung_i.backend.global.geojson.dto.Local
import kr.motung_i.backend.persistence.tour.entity.Country
import org.springframework.core.io.support.PathMatchingResourcePatternResolver
import org.springframework.stereotype.Component

@Component
class LocalsCache(
    private val localsFactory: LocalsFactory,
) {
    private lateinit var locals: List<Local>

    @PostConstruct
    private fun init() {
        val resolver = PathMatchingResourcePatternResolver()
        val resources = resolver.getResources("classpath:geojson/**/*.geojson")

        locals = resources.map { localsFactory.toDto(it) }
    }

    fun findLocalByCountry(country: Country): Local? =
        locals.firstOrNull { it.country == country }
}