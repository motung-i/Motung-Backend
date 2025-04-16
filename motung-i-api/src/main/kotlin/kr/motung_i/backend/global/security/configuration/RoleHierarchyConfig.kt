package kr.motung_i.backend.global.security.configuration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.access.hierarchicalroles.RoleHierarchy
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl

@Configuration
class RoleHierarchyConfig {

    @Bean
    fun roleHierarchy() : RoleHierarchy =
        RoleHierarchyImpl.fromHierarchy("ROLE_ADMIN > ROLE_USER")
}