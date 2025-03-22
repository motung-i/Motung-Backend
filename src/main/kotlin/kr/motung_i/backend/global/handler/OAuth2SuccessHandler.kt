package kr.motung_i.backend.global.handler

import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import kr.motung_i.backend.domain.user.entity.RefreshToken
import kr.motung_i.backend.domain.user.entity.User
import kr.motung_i.backend.domain.user.repository.RefreshTokenRepository
import kr.motung_i.backend.domain.user.repository.UserRepository
import kr.motung_i.backend.global.provider.TokenProvider
import org.springframework.security.core.Authentication
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler
import org.springframework.stereotype.Component

@Component
class OAuth2SuccessHandler(
    private val refreshTokenRepository: RefreshTokenRepository,
    private val tokenProvider: TokenProvider,
    private val userRepository: UserRepository,
    val objectMapper: ObjectMapper,
) : SimpleUrlAuthenticationSuccessHandler() {
    override fun onAuthenticationSuccess(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        authentication: Authentication?,
    ) {
        val clientId: String = (authentication?.principal as OAuth2User).attributes["sub"].toString()
        val user: User = userRepository.findByOauthId(clientId).orElseThrow()
        val accessToken: String =
            tokenProvider.generateToken(
                clientId = clientId,
                role = user.roles,
                isRefresh = false,
            )
        val refreshToken: String =
            tokenProvider.generateToken(
                clientId = clientId,
                role = user.roles,
                isRefresh = true,
            )
        refreshTokenRepository.save(
            RefreshToken(
                clientId = clientId,
                refreshToken = refreshToken,
                timeToLive = System.currentTimeMillis() + 1000 * 60 * 60 * 2L,
            ),
        )

        response?.contentType = "application/json"
        response?.characterEncoding = "UTF-8"

        val tokenResponse =
            mapOf(
                "accessToken" to accessToken,
                "refreshToken" to refreshToken,
            )

        response?.writer?.write(objectMapper.writeValueAsString(tokenResponse))
    }
}
