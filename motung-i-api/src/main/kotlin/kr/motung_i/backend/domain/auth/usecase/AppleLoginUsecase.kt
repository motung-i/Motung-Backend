package kr.motung_i.backend.domain.auth.usecase

import kr.motung_i.backend.domain.auth.presentation.dto.request.AppleLoginRequest
import kr.motung_i.backend.domain.auth.presentation.dto.response.impl.TokenResponseData

interface AppleLoginUsecase {
    fun execute(request: AppleLoginRequest): TokenResponseData
}
