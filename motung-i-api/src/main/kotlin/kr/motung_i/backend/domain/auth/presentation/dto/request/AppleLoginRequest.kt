package kr.motung_i.backend.domain.auth.presentation.dto.request

data class AppleLoginRequest(
    val idToken: String,
    val deviceToken: String?,
)
