package kr.motung_i.backend.domain.tour.formatter.configuration

import kr.motung_i.backend.domain.tour.formatter.TourFormatter
import kr.motung_i.backend.domain.tour.formatter.impl.KoreaTourFormatterImpl
import kr.motung_i.backend.global.geojson.enums.Country
import kr.motung_i.backend.global.geojson.enums.Country.*
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class TourFormatterConfig {

    @Bean
    fun tourFormatterMapConfig(): Map<Country, TourFormatter> = mapOf(
        KOREA to KoreaTourFormatterImpl()
    )
}