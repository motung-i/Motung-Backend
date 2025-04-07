package kr.motung_i.backend.domain.user.presentation.usecase

import kr.motung_i.backend.global.exception.CustomException
import kr.motung_i.backend.global.exception.enums.CustomErrorCode
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
        val oauthId = SecurityContextHolder.getContext().authentication.name
        return userRepository.findByOauthId(oauthId) ?: throw CustomException(CustomErrorCode.NOT_FOUND_USER)
    }
}