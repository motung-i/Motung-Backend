package kr.motung_i.backend.domain.auth.usecase

import kr.motung_i.backend.domain.auth.presentation.dto.request.GoogleLoginRequest
import kr.motung_i.backend.domain.auth.presentation.dto.response.impl.TokenResponseData
import kr.motung_i.backend.global.security.provider.JwtTokenProvider
import kr.motung_i.backend.global.third_party.google.GoogleOauthInfoFeignClient
import kr.motung_i.backend.persistence.auth.entity.RefreshToken
import kr.motung_i.backend.persistence.auth.repository.RefreshTokenCustomRepository
import kr.motung_i.backend.persistence.device_token.DeviceTokenRepository
import kr.motung_i.backend.persistence.device_token.entity.DeviceToken
import kr.motung_i.backend.persistence.user.entity.User
import kr.motung_i.backend.persistence.user.entity.enums.Provider
import kr.motung_i.backend.persistence.user.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class GoogleLoginUsecase(
    private val jwtTokenProvider: JwtTokenProvider,
    private val userRepository: UserRepository,
    private val deviceTokenRepository: DeviceTokenRepository,
    private val googleOauthInfoFeignClient: GoogleOauthInfoFeignClient,
    private val refreshTokenRepository: RefreshTokenCustomRepository,
) {
    fun execute(request: GoogleLoginRequest): TokenResponseData {
        val result = googleOauthInfoFeignClient.getInfo("Bearer ${request.accessToken}")
        val user =
            userRepository.findByOauthId(result.id) ?: userRepository.save(
                User(
                    email = result.email,
                    oauthId = result.id,
                    provider = Provider.GOOGLE,
                ),
            )
        val accessToken =
            jwtTokenProvider.generateToken(
                userId = user.id!!,
                role = user.role,
                isRefresh = false,
            )
        val refreshToken =
            jwtTokenProvider.generateToken(
                userId = user.id!!,
                role = user.role,
                isRefresh = true,
            )
        refreshTokenRepository.save(
            RefreshToken(
                userId = user.id.toString(),
                refreshToken = refreshToken,
                timeToLive = System.currentTimeMillis() + 1000 * 60 * 60 * 2L,
            ),
        )
        request.deviceToken?.let {
            deviceTokenRepository.save(
                DeviceToken(
                    userId = user.id!!,
                    deviceToken = it,
                ),
            )
        }
        return TokenResponseData(
            accessToken,
            refreshToken,
        )
    }
}
