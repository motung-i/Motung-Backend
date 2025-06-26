package kr.motung_i.backend.domain.user.usecase.impl

import kr.motung_i.backend.domain.user.presentation.dto.request.UpdateNicknameRequest
import kr.motung_i.backend.domain.user.usecase.FetchCurrentUserUsecase
import kr.motung_i.backend.domain.user.usecase.UpdateNicknameUsecase
import kr.motung_i.backend.global.exception.CustomException
import kr.motung_i.backend.global.exception.enums.CustomErrorCode
import kr.motung_i.backend.persistence.user.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class UpdateNicknameUsecaseImpl(
    private val fetchCurrentUserUsecase: FetchCurrentUserUsecase,
    private val userRepository: UserRepository,
): UpdateNicknameUsecase {
    override fun execute(request: UpdateNicknameRequest) {
        val currentUser = fetchCurrentUserUsecase.execute()

        if (userRepository.existsByNickname(request.nickname)) {
            throw CustomException(CustomErrorCode.ALREADY_EXISTS_NICKNAME)
        }

        currentUser.updateNickname(request.nickname)
        userRepository.save(currentUser)
    }
}