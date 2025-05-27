package kr.motung_i.backend.global.security.configuration

import com.fasterxml.jackson.databind.ObjectMapper
import kr.motung_i.backend.global.exception.filter.ExceptionFilter
import kr.motung_i.backend.global.security.exception.CustomAccessDeniedHandler
import kr.motung_i.backend.global.security.exception.CustomAuthenticationEntryPoint
import kr.motung_i.backend.global.security.filter.JwtAuthFilter
import kr.motung_i.backend.global.security.filter.SuspensionUserFilter
import kr.motung_i.backend.global.security.service.CustomOauth2Service
import kr.motung_i.backend.global.security.success.OAuth2SuccessHandler
import kr.motung_i.backend.persistence.user.entity.enums.Role
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

@Configuration
class SecurityConfig {
    @Bean
    fun securityFilterChain(
        http: HttpSecurity,
        objectMapper: ObjectMapper,
        oauth2Service: CustomOauth2Service,
        oAuth2SuccessHandler: OAuth2SuccessHandler,
        suspensionUserFilter: SuspensionUserFilter,
        jwtAuthFilter: JwtAuthFilter,
        exceptionFilter: ExceptionFilter,
    ): SecurityFilterChain =
        http
            .oauth2Login { oauth2 -> oauth2.successHandler(oAuth2SuccessHandler).userInfoEndpoint { it.userService(oauth2Service) } }
            .addFilterBefore(suspensionUserFilter, UsernamePasswordAuthenticationFilter::class.java)
            .addFilterBefore(jwtAuthFilter, suspensionUserFilter::class.java)
            .addFilterBefore(exceptionFilter, jwtAuthFilter::class.java)
            .authorizeHttpRequests {
                it
                    // 운영
                    .requestMatchers(HttpMethod.GET, "/")
                    .permitAll()
                    .requestMatchers(HttpMethod.GET, "/actuator/prometheus")
                    .hasAuthority(Role.ROLE_ADMIN.name)
                    // 로그인
                    .requestMatchers(HttpMethod.GET, "/auth/check-register")
                    .authenticated()
                    .requestMatchers(HttpMethod.POST, "/auth/register")
                    .hasAuthority(Role.ROLE_PENDING.name)
                    .requestMatchers(HttpMethod.POST, "/auth/login")
                    .permitAll()
                    .requestMatchers(HttpMethod.POST, "/auth/refresh")
                    .permitAll()
                    // 전역
                    .requestMatchers("/admin/**")
                    .hasAuthority(Role.ROLE_ADMIN.name)
                    .anyRequest()
                    .hasAuthority(Role.ROLE_USER.name)
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
            }.build()

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
