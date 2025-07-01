package kr.motung_i.backend.domain.user.usecase.impl

import kr.motung_i.backend.domain.user.usecase.CreateUserUsecase
import kr.motung_i.backend.persistence.user.entity.User
import kr.motung_i.backend.persistence.user.entity.enums.Role
import kr.motung_i.backend.persistence.user.repository.UserRepository
import kr.motung_i.backend.persistence.user.util.HashUtil.Companion.sha256
import kr.motung_i.backend.persistence.user.util.HashUtil.Companion.toHex
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class CreateUserUsecaseImpl(
    private val userRepository: UserRepository,
): CreateUserUsecase {
    override fun execute(user: User): User {
        val hashedOauthId = user.oauthId.sha256().toHex()
        val savedUser = userRepository.findByOauthIdAndProvider(hashedOauthId, user.provider)

        return if (savedUser != null && savedUser.role == Role.ROLE_REMOVED) {
            savedUser.restore(user)
            userRepository.save(savedUser)
        } else {
            userRepository.save(user)
        }
    }
}