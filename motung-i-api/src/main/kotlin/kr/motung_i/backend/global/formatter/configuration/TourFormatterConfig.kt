package kr.motung_i.backend.global.formatter.configuration

import kr.motung_i.backend.global.formatter.TourFormatter
import kr.motung_i.backend.global.formatter.impl.KoreaTourFormatterImpl
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