package kr.motung_i.backend.domain.auth.usecase

import kr.motung_i.backend.domain.auth.presentation.dto.response.impl.TokenRequest
import kr.motung_i.backend.domain.item.presentation.dto.response.TokenResponse
import kr.motung_i.backend.global.exception.CustomException
import kr.motung_i.backend.global.exception.enums.CustomErrorCode
import kr.motung_i.backend.global.security.provider.JwtTokenProvider
import kr.motung_i.backend.persistence.auth.entity.RefreshToken
import kr.motung_i.backend.persistence.auth.repository.RefreshTokenCustomRepository
import kr.motung_i.backend.persistence.user.entity.User
import kr.motung_i.backend.persistence.user.repository.UserCustomRepository
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class ReissueTokenUsecase(
    val userCustomRepository: UserCustomRepository,
    val refreshTokenCustomRepository: RefreshTokenCustomRepository,
    val jwtTokenProvider: JwtTokenProvider,
) {
    fun execute(tokenRequest: TokenRequest): TokenResponse {
        val result: RefreshToken =
            refreshTokenCustomRepository
                .find(
                    tokenRequest.refreshToken,
                ).orElseThrow {
                    CustomException(CustomErrorCode.UNAUTHORIZED)
                }
        refreshTokenCustomRepository.delete(tokenRequest.refreshToken)
        val userId = UUID.fromString(result.userId)
        val user: User =
            userCustomRepository.findByUserId(userId).orElseThrow {
                CustomException(CustomErrorCode.NOT_FOUND_USER)
            }
        val accessToken = jwtTokenProvider.generateToken(userId = userId, role = user.role, isRefresh = false)
        val refreshToken = jwtTokenProvider.generateToken(userId = userId, role = user.role, isRefresh = true)
        return TokenResponse.from(accessToken = accessToken, refreshToken = refreshToken)
    }
}
