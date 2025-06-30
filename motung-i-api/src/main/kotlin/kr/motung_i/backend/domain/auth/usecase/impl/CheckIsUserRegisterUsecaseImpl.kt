package kr.motung_i.backend.domain.auth.usecase.impl

import kr.motung_i.backend.domain.auth.presentation.dto.response.impl.CheckRegisterResponse
import kr.motung_i.backend.domain.auth.usecase.CheckIsUserRegisterUsecase
import kr.motung_i.backend.domain.user.usecase.FetchCurrentUserUsecase
import kr.motung_i.backend.persistence.user.entity.User
import org.springframework.stereotype.Service

@Service
class CheckIsUserRegisterUsecaseImpl(
    private val fetchCurrentUserUsecase: FetchCurrentUserUsecase
): CheckIsUserRegisterUsecase {
    override fun execute(): CheckRegisterResponse {
        val loginUser: User = fetchCurrentUserUsecase.execute()
        return CheckRegisterResponse(
            isUserRegistered = loginUser.nickname != null
        )
    }
}
