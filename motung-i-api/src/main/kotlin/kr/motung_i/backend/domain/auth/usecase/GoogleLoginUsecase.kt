package kr.motung_i.backend.domain.auth.usecase

import kr.motung_i.backend.domain.auth.presentation.dto.request.GoogleLoginRequest
import kr.motung_i.backend.domain.auth.presentation.dto.response.impl.TokenResponseData

interface GoogleLoginUsecase {
    fun execute(request: GoogleLoginRequest): TokenResponseData
}