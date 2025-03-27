package kr.motung_i.backend.global.security.filter

import jakarta.servlet.FilterChain
import jakarta.servlet.ServletRequest
import jakarta.servlet.ServletResponse
import jakarta.servlet.http.HttpServletRequest
import kr.motung_i.backend.global.security.provider.JwtTokenProvider
import kr.motung_i.backend.persistence.user.entity.enums.Role
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.GenericFilterBean

@Component
class JwtAuthFilter(
    private val jwtTokenProvider: JwtTokenProvider,
) : GenericFilterBean() {
    override fun doFilter(
        p0: ServletRequest?,
        p1: ServletResponse?,
        p2: FilterChain?,
    ) {
        val token = jwtTokenProvider.tokenResolveByRequest(p0 as HttpServletRequest)
        if (token != null && jwtTokenProvider.validateToken(token = token, isRefresh = false)) {
            val clientId: String = jwtTokenProvider.getClientId(token = token, isRefresh = false)
            val role: Role = jwtTokenProvider.getUserRoles(token = token, isRefresh = false)
            val auth: Authentication =
                UsernamePasswordAuthenticationToken(clientId, "", mutableListOf(SimpleGrantedAuthority(role.toString())))
            SecurityContextHolder.getContext().authentication = auth
        }
        p2?.doFilter(p0, p1)
    }
}
