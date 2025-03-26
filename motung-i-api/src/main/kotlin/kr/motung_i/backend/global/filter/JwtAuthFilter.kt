package kr.motung_i.backend.global.filter

import jakarta.servlet.FilterChain
import jakarta.servlet.ServletRequest
import jakarta.servlet.ServletResponse
import jakarta.servlet.http.HttpServletRequest
import kr.motung_i.backend.persistence.user.entity.enums.Roles
import kr.motung_i.backend.global.provider.TokenProvider
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.GenericFilterBean

@Component
class JwtAuthFilter(
    private val tokenProvider: TokenProvider,
) : GenericFilterBean() {
    override fun doFilter(
        p0: ServletRequest?,
        p1: ServletResponse?,
        p2: FilterChain?,
    ) {
        val token = tokenProvider.tokenResolveByRequest(p0 as HttpServletRequest)
        if (token != null && tokenProvider.validateToken(token = token, isRefresh = false)) {
            val clientId: String = tokenProvider.getClientId(token = token, isRefresh = false)
            val roles: Roles = tokenProvider.getUserRoles(token = token, isRefresh = false)
            val auth: Authentication =
                UsernamePasswordAuthenticationToken(clientId, "", mutableListOf(SimpleGrantedAuthority(roles.toString())))
            SecurityContextHolder.getContext().authentication = auth
        }
        p2?.doFilter(p0, p1)
    }
}
