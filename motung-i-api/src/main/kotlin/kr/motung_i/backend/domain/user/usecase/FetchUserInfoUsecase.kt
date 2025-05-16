package kr.motung_i.backend.domain.user.usecase

import kr.motung_i.backend.domain.user.presentation.dto.response.FetchUserInfoResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class FetchUserInfoUsecase(
    private val fetchCurrentUserUsecase: FetchCurrentUserUsecase
) {
    fun execute(): FetchUserInfoResponse {
        val currentUser = fetchCurrentUserUsecase.execute()
        return FetchUserInfoResponse.fromUser(currentUser)
    }
}