package kr.motung_i.backend.global.exception.configuration

import com.fasterxml.jackson.databind.ObjectMapper
import kr.motung_i.backend.global.exception.filter.ExceptionFilter
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ExceptionConfig {
    @Bean
    fun exceptionFilter(objectMapper: ObjectMapper): FilterRegistrationBean<ExceptionFilter> =
        FilterRegistrationBean<ExceptionFilter>().apply {
            filter = ExceptionFilter(objectMapper)
            order = 1
            urlPatterns = mutableListOf("*")
        }
}