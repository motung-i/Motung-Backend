package kr.motung_i.backend.domain.item.presentation.dto.response

import kr.motung_i.backend.domain.auth.presentation.dto.response.impl.TokenResponseData

interface TokenResponse {
    val accessToken: String
    val refreshToken: String

    companion object {
        fun from(
            accessToken: String,
            refreshToken: String,
        ): TokenResponse = TokenResponseData(accessToken, refreshToken)
    }
}
