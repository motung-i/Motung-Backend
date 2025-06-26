package kr.motung_i.backend.domain.auth.usecase

import kr.motung_i.backend.domain.auth.presentation.dto.response.impl.CheckRegisterResponse

interface CheckIsUserRegisterUsecase {
    fun execute(): CheckRegisterResponse
}