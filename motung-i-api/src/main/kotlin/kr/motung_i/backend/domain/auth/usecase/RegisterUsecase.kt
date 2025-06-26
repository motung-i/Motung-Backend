package kr.motung_i.backend.domain.auth.usecase

import kr.motung_i.backend.domain.auth.presentation.dto.response.impl.RegisterRequest

interface RegisterUsecase {
    fun execute(request: RegisterRequest)
}