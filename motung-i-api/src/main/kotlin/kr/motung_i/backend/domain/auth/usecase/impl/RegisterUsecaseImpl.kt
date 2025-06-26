package kr.motung_i.backend.domain.auth.usecase.impl

import kr.motung_i.backend.domain.auth.presentation.dto.response.impl.RegisterRequest
import kr.motung_i.backend.domain.auth.usecase.RegisterUsecase
import kr.motung_i.backend.persistence.user.entity.User
import kr.motung_i.backend.persistence.user.repository.UserRepository
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service

@Service
class RegisterUsecaseImpl(
    private val userCustomRepository: UserRepository,
): RegisterUsecase {
    override fun execute(request: RegisterRequest) {
        val loginUser: User = SecurityContextHolder.getContext().authentication.principal as User
        loginUser.updateNickname(request.nickname)
        loginUser.approve()

        userCustomRepository.save(loginUser)
    }
}
