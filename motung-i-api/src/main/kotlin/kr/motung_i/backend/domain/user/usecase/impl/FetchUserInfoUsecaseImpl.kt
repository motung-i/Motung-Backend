package kr.motung_i.backend.domain.user.usecase.impl

import kr.motung_i.backend.domain.user.presentation.dto.response.FetchUserInfoResponse
import kr.motung_i.backend.domain.user.usecase.FetchCurrentUserUsecase
import kr.motung_i.backend.domain.user.usecase.FetchUserInfoUsecase
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class FetchUserInfoUsecaseImpl(
    private val fetchCurrentUserUsecase: FetchCurrentUserUsecase
): FetchUserInfoUsecase {
    override fun execute(): FetchUserInfoResponse {
        val currentUser = fetchCurrentUserUsecase.execute()
        return FetchUserInfoResponse.fromUser(currentUser)
    }
}