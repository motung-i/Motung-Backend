package kr.motung_i.backend.domain.auth.usecase.impl

import kr.motung_i.backend.domain.auth.presentation.dto.response.impl.TokenRequest
import kr.motung_i.backend.domain.auth.usecase.LogoutUsecase
import kr.motung_i.backend.domain.user.usecase.FetchCurrentUserUsecase
import kr.motung_i.backend.global.exception.CustomException
import kr.motung_i.backend.global.exception.enums.CustomErrorCode
import kr.motung_i.backend.global.security.provider.JwtTokenProvider
import kr.motung_i.backend.persistence.auth.repository.RefreshTokenCustomRepository
import kr.motung_i.backend.persistence.user.entity.User
import org.springframework.stereotype.Service

@Service
class LogoutUsecaseImpl(
    private val fetchCurrentUserUsecase: FetchCurrentUserUsecase,
    private val jwtTokenProvider: JwtTokenProvider,
    private val refreshTokenCustomRepository: RefreshTokenCustomRepository,
): LogoutUsecase {
    override fun execute(request: TokenRequest) {
        val loginUser: User = fetchCurrentUserUsecase.execute()
        val tokenUserId: String = jwtTokenProvider.getUserId(token = request.refreshToken, isRefresh = true)
        if (loginUser.id.toString() != tokenUserId) {
            throw CustomException(customErrorCode = CustomErrorCode.NOT_FOUND_USER)
        }
        refreshTokenCustomRepository.delete(tokenUserId)
    }
}
