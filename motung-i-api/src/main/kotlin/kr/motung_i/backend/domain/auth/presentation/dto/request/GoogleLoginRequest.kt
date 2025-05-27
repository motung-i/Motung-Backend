package kr.motung_i.backend.domain.auth.presentation.dto.request

data class GoogleLoginRequest(
    val accessToken: String,
    val deviceToken: String?,
)
