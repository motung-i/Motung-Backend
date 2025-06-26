package kr.motung_i.backend.domain.auth.usecase

import kr.motung_i.backend.domain.auth.presentation.dto.response.impl.TokenRequest

interface LogoutUsecase {
    fun execute(request: TokenRequest)
}