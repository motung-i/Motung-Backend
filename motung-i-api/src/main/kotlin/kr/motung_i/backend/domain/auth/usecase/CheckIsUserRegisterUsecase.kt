package kr.motung_i.backend.domain.auth.usecase

import kr.motung_i.backend.persistence.user.entity.User
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service

@Service
class CheckIsUserRegisterUsecase {
    fun execute(): Boolean {
        val loginUser: User = SecurityContextHolder.getContext().authentication.principal as User
        return loginUser.name.isNotEmpty()
    }
}
