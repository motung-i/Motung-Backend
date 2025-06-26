package kr.motung_i.backend.domain.auth.usecase

import kr.motung_i.backend.domain.auth.presentation.dto.response.impl.TokenRequest
import kr.motung_i.backend.domain.item.presentation.dto.response.TokenResponse

interface ReissueTokenUsecase {
    fun execute(request: TokenRequest): TokenResponse
}