package kr.motung_i.backend.domain.auth.usecase.impl

import kr.motung_i.backend.domain.auth.presentation.dto.response.impl.RegisterRequest
import kr.motung_i.backend.domain.auth.usecase.RegisterUsecase
import kr.motung_i.backend.domain.user.usecase.FetchCurrentUserUsecase
import kr.motung_i.backend.persistence.user.entity.User
import kr.motung_i.backend.persistence.user.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class RegisterUsecaseImpl(
    private val userCustomRepository: UserRepository,
    private val fetchCurrentUserUsecase: FetchCurrentUserUsecase,
): RegisterUsecase {
    override fun execute(request: RegisterRequest) {
        val loginUser: User = fetchCurrentUserUsecase.execute()
        loginUser.updateNickname(request.nickname)
        loginUser.approve()

        userCustomRepository.save(loginUser)
    }
}
