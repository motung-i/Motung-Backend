package kr.motung_i.backend.domain.user.usecase.impl

import kr.motung_i.backend.domain.user.usecase.FetchCurrentUserUsecase
import kr.motung_i.backend.domain.user.usecase.RemoveUserUsecase
import kr.motung_i.backend.persistence.user.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class RemoveUserUsecaseImpl(
    private val userRepository: UserRepository,
    private val fetchCurrentUserUsecase: FetchCurrentUserUsecase,
): RemoveUserUsecase {
    override fun execute() {
        val user = fetchCurrentUserUsecase.execute()
        user.remove()
        userRepository.save(user)
    }
}