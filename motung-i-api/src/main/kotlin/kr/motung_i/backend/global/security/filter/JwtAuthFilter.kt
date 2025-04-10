package kr.motung_i.backend.global.security.filter

import jakarta.servlet.FilterChain
import jakarta.servlet.ServletRequest
import jakarta.servlet.ServletResponse
import jakarta.servlet.http.HttpServletRequest
import kr.motung_i.backend.global.exception.CustomException
import kr.motung_i.backend.global.exception.enums.CustomErrorCode
import kr.motung_i.backend.global.security.provider.JwtTokenProvider
import kr.motung_i.backend.persistence.user.entity.User
import kr.motung_i.backend.persistence.user.repository.UserRepository
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.GenericFilterBean
import java.util.*

@Component
class JwtAuthFilter(
    private val jwtTokenProvider: JwtTokenProvider,
    private val userRepository: UserRepository,
) : GenericFilterBean() {
    override fun doFilter(
        p0: ServletRequest?,
        p1: ServletResponse?,
        p2: FilterChain?,
    ) {
        val token = jwtTokenProvider.tokenResolveByRequest(p0 as HttpServletRequest)
        if (token == null) {
            p2?.doFilter(p0, p1)
            return
        }
        if (jwtTokenProvider.validateToken(token = token, isRefresh = false)) {
            val userId: String = jwtTokenProvider.getUserId(token = token, isRefresh = false)
            val user: User =
                userRepository.findByUserId(userId = UUID.fromString(userId)).orElseThrow {
                    CustomException(CustomErrorCode.NOT_FOUND_USER)
                }
            val authentication =
                UsernamePasswordAuthenticationToken(user, null, mutableListOf(SimpleGrantedAuthority(user.role.toString())))
            SecurityContextHolder.getContext().authentication = authentication
        }
        p2?.doFilter(p0, p1)
    }
}
