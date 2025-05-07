package kr.motung_i.backend.domain.user.usecase

import kr.motung_i.backend.domain.user.presentation.dto.request.UpdateNicknameRequest
import kr.motung_i.backend.global.exception.CustomException
import kr.motung_i.backend.global.exception.enums.CustomErrorCode
import kr.motung_i.backend.persistence.user.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class UpdateNicknameUsecase(
    private val fetchCurrentUserUsecase: FetchCurrentUserUsecase,
    private val userRepository: UserRepository,
) {
    fun execute(request: UpdateNicknameRequest) {
        val currentUser = fetchCurrentUserUsecase.execute()

        if (userRepository.existsByName(request.nickname)) {
            throw CustomException(CustomErrorCode.ALREADY_EXISTS_NICKNAME)
        }

        userRepository.save(
            currentUser.copy(name = request.nickname)
        )
    }
}