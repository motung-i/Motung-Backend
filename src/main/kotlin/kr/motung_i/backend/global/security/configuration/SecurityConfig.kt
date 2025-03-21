package kr.motung_i.backend.global.security.configuration

import com.fasterxml.jackson.databind.ObjectMapper
import kr.motung_i.backend.global.security.exception.CustomAccessDeniedHandler
import kr.motung_i.backend.global.security.exception.CustomAuthenticationEntryPoint
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

@Configuration
class SecurityConfig {
    @Bean
    fun securityFilterChain(http: HttpSecurity, objectMapper: ObjectMapper): SecurityFilterChain {
        return http
            .authorizeHttpRequests {
                it
                    .requestMatchers(HttpMethod.GET, "/")
                    .permitAll()
                    .requestMatchers(HttpMethod.GET, "/error")
                    .permitAll()
            }.csrf {
                it.disable()
            }.formLogin {
                it.disable()
            }.httpBasic {
                it.disable()
            }.sessionManagement {
                it.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            }.cors {
                it.configurationSource(corsConfig())
            }.exceptionHandling {
                it
                    .authenticationEntryPoint(CustomAuthenticationEntryPoint(objectMapper))
                    .accessDeniedHandler(CustomAccessDeniedHandler(objectMapper))
            }
            .build()
    }

    fun corsConfig(): CorsConfigurationSource {
        val corsConfigurationSource = CorsConfiguration()
        corsConfigurationSource.addAllowedHeader("*")
        corsConfigurationSource.addAllowedMethod("*")
        corsConfigurationSource.addAllowedOriginPattern("*")
        corsConfigurationSource.addExposedHeader("*")
        corsConfigurationSource.allowCredentials = true

        val urlBasedCorsConfigurationSource = UrlBasedCorsConfigurationSource()

        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfigurationSource)
        return urlBasedCorsConfigurationSource
    }
}