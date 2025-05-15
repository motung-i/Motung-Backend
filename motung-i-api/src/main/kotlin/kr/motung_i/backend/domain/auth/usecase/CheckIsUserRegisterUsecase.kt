package kr.motung_i.backend.domain.auth.usecase

import kr.motung_i.backend.domain.auth.presentation.dto.response.impl.CheckRegisterResponse
import kr.motung_i.backend.persistence.user.entity.User
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service

@Service
class CheckIsUserRegisterUsecase {
    fun execute(): CheckRegisterResponse {
        val loginUser: User = SecurityContextHolder.getContext().authentication.principal as User
        return CheckRegisterResponse(
            isUserRegistered = loginUser.nickname != null
        )
    }
}
