package kr.motung_i.backend.domain.user.usecase

import kr.motung_i.backend.domain.user.presentation.dto.response.FetchUserInfoResponse

interface FetchUserInfoUsecase {
    fun execute(): FetchUserInfoResponse
}