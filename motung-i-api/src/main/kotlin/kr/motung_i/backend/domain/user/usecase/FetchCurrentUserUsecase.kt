package kr.motung_i.backend.domain.user.usecase

import kr.motung_i.backend.persistence.user.entity.User
import kr.motung_i.backend.persistence.user.repository.UserRepository
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class FetchCurrentUserUsecase(
    private val userRepository: UserRepository,
) {
    fun execute(): User {
        return SecurityContextHolder.getContext().authentication.principal as User
    }
}