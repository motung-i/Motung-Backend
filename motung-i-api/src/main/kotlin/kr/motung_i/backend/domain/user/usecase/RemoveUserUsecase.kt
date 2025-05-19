package kr.motung_i.backend.domain.user.usecase

import kr.motung_i.backend.persistence.user.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class RemoveUserUsecase(
    private val userRepository: UserRepository,
    private val fetchCurrentUserUsecase: FetchCurrentUserUsecase,
) {
    fun execute() {
        val user = fetchCurrentUserUsecase.execute()
        user.remove()
        userRepository.save(user)
    }
}