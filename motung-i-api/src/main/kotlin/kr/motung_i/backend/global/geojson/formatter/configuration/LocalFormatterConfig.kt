package kr.motung_i.backend.global.geojson.formatter.configuration

import kr.motung_i.backend.global.geojson.formatter.LocalFormatter
import kr.motung_i.backend.global.geojson.formatter.impl.KoreaLocalFormatterImpl
import kr.motung_i.backend.persistence.tour_location.entity.Country
import kr.motung_i.backend.persistence.tour_location.entity.Country.*
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class LocalFormatterConfig {

    @Bean
    fun localFormatterMapConfig(): Map<Country, LocalFormatter> = mapOf(
        KOREA to KoreaLocalFormatterImpl()
    )
}