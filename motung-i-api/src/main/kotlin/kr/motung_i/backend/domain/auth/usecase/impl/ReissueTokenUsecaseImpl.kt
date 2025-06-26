package kr.motung_i.backend.domain.auth.usecase.impl

import kr.motung_i.backend.domain.auth.presentation.dto.response.impl.TokenRequest
import kr.motung_i.backend.domain.auth.usecase.ReissueTokenUsecase
import kr.motung_i.backend.domain.item.presentation.dto.response.TokenResponse
import kr.motung_i.backend.global.exception.CustomException
import kr.motung_i.backend.global.exception.enums.CustomErrorCode
import kr.motung_i.backend.global.security.provider.JwtTokenProvider
import kr.motung_i.backend.persistence.auth.entity.RefreshToken
import kr.motung_i.backend.persistence.auth.repository.RefreshTokenCustomRepository
import kr.motung_i.backend.persistence.user.entity.User
import kr.motung_i.backend.persistence.user.repository.UserRepository
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class ReissueTokenUsecaseImpl(
    val userCustomRepository: UserRepository,
    val refreshTokenCustomRepository: RefreshTokenCustomRepository,
    val jwtTokenProvider: JwtTokenProvider,
    val tokenRepository: RefreshTokenCustomRepository,
): ReissueTokenUsecase {
    override fun execute(request: TokenRequest): TokenResponse {
        val result: RefreshToken =
            refreshTokenCustomRepository
                .find(
                    request.refreshToken,
                ).orElseThrow {
                    CustomException(CustomErrorCode.UNAUTHORIZED)
                }
        refreshTokenCustomRepository.delete(request.refreshToken)
        val userId = UUID.fromString(result.userId)
        val user: User =
            userCustomRepository.findByUserId(userId).orElseThrow {
                CustomException(CustomErrorCode.NOT_FOUND_USER)
            }
        val accessToken = jwtTokenProvider.generateToken(userId = userId, role = user.role, isRefresh = false)
        val refreshToken = jwtTokenProvider.generateToken(userId = userId, role = user.role, isRefresh = true)
        tokenRepository.save(
            RefreshToken(
                userId = user.id.toString(),
                refreshToken = refreshToken,
                timeToLive = System.currentTimeMillis() + 1000 * 60 * 60 * 2L,
            ),
        )
        return TokenResponse.from(accessToken = accessToken, refreshToken = refreshToken)
    }
}
