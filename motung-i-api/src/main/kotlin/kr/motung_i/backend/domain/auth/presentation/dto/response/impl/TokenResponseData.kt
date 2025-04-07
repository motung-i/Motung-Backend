package kr.motung_i.backend.domain.auth.presentation.dto.response.impl

import kr.motung_i.backend.domain.item.presentation.dto.response.TokenResponse

data class TokenResponseData(
    override val accessToken: String,
    override val refreshToken: String,
) : TokenResponse
