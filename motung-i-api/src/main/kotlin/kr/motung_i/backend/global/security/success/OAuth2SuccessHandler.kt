package kr.motung_i.backend.global.security.success

import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import kr.motung_i.backend.global.security.provider.JwtTokenProvider
import kr.motung_i.backend.persistence.auth.entity.RefreshToken
import kr.motung_i.backend.persistence.auth.repository.RefreshTokenCustomRepository
import kr.motung_i.backend.persistence.user.entity.User
import kr.motung_i.backend.persistence.user.repository.UserCustomRepository
import org.springframework.security.core.Authentication
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler
import org.springframework.stereotype.Component

@Component
class OAuth2SuccessHandler(
    private val refreshTokenRepository: RefreshTokenCustomRepository,
    private val jwtTokenProvider: JwtTokenProvider,
    private val userRepository: UserCustomRepository,
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
            jwtTokenProvider.generateToken(
                clientId = user.id.toString(),
                role = user.role,
                isRefresh = false,
            )
        val refreshToken: String =
            jwtTokenProvider.generateToken(
                clientId = user.id.toString(),
                role = user.role,
                isRefresh = true,
            )
        refreshTokenRepository.save(
            RefreshToken(
                clientId = user.id.toString(),
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
