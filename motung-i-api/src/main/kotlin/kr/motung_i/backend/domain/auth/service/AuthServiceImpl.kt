package kr.motung_i.backend.domain.auth.service

import kr.motung_i.backend.domain.item.presentation.dto.response.TokenResponse
import kr.motung_i.backend.global.exception.CustomException
import kr.motung_i.backend.global.exception.enums.CustomErrorCode
import kr.motung_i.backend.global.security.provider.JwtTokenProvider
import kr.motung_i.backend.persistence.auth.entity.RefreshToken
import kr.motung_i.backend.persistence.auth.repository.RefreshTokenCustomRepository
import kr.motung_i.backend.persistence.user.entity.User
import kr.motung_i.backend.persistence.user.repository.UserCustomRepository
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class AuthServiceImpl(
    val userCustomRepository: UserCustomRepository,
    val refreshTokenCustomRepository: RefreshTokenCustomRepository,
    val jwtTokenProvider: JwtTokenProvider,
) : AuthService {
    override fun logout(refreshToken: String) {
        val loginUser: Authentication = SecurityContextHolder.getContext().authentication
        val tokenClientId: String = jwtTokenProvider.getClientId(token = refreshToken, isRefresh = true)
        if (loginUser.principal != tokenClientId) {
            throw CustomException(customErrorCode = CustomErrorCode.FORBIDDEN, detailMessage = "존재하지 않는 유저입니다.")
        }
        refreshTokenCustomRepository.delete(tokenClientId)
    }

    override fun isUserRegister(): Boolean {
        val loginUser: Authentication = SecurityContextHolder.getContext().authentication
        val user: User = userCustomRepository.findByUserId(UUID.fromString(loginUser.principal.toString())).orElseThrow()
        return user.name.isNotEmpty()
    }

    override fun register(name: String) {
        val loginUser: Authentication = SecurityContextHolder.getContext().authentication
        val user: User = userCustomRepository.findByUserId(loginUser.principal as UUID).orElseThrow()
        userCustomRepository.save(user.copy(name = name))
    }

    override fun reissueToken(resolveRefreshToken: String): TokenResponse {
        val loginUser: Authentication = SecurityContextHolder.getContext().authentication
        val clientId: UUID = UUID.fromString(loginUser.principal.toString())
        val result: RefreshToken =
            refreshTokenCustomRepository.find(
                refreshToken = resolveRefreshToken,
                clientId = clientId.toString(),
            )
        val user: User = userCustomRepository.findByUserId(clientId = clientId).orElseThrow()
        val accessToken = jwtTokenProvider.generateToken(clientId = result.clientId, role = user.role, isRefresh = false)
        val refreshToken = jwtTokenProvider.generateToken(clientId = result.clientId, role = user.role, isRefresh = false)
        return TokenResponse.from(accessToken = accessToken, refreshToken = refreshToken)
    }
}
