package kr.motung_i.backend.global.config

import kr.motung_i.backend.global.service.CustomOauth2Service
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain

@Configuration
class SecurityConfig {
    @Bean
    fun securityFilterChain(
        http: HttpSecurity,
        oauth2Service: CustomOauth2Service,
    ): SecurityFilterChain =
        http
            .formLogin { it.disable() }
            .cors { it.disable() }
            .csrf { it.disable() }
            .httpBasic { it.disable() }
            .logout { it.disable() }
            .headers { headersConfigurer -> headersConfigurer.frameOptions { it.disable() } }
            .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
            .authorizeHttpRequests {
                it
                    .requestMatchers(HttpMethod.GET, "/")
                    .permitAll()
            }.oauth2Login { oauth2 -> oauth2.userInfoEndpoint { it.userService(oauth2Service) } }
            .build()
}
