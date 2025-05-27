package kr.motung_i.backend.domain.auth.usecase

import kr.motung_i.backend.domain.auth.presentation.dto.response.impl.RegisterRequest
import kr.motung_i.backend.persistence.user.entity.User
import kr.motung_i.backend.persistence.user.repository.UserRepository
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service

@Service
class RegisterUsecase(
    private val userCustomRepository: UserRepository,
) {
    fun execute(request: RegisterRequest) {
        val loginUser: User = SecurityContextHolder.getContext().authentication.principal as User
        loginUser.updateNickname(request.nickname)
        loginUser.approve()

        userCustomRepository.save(loginUser)
    }
}
