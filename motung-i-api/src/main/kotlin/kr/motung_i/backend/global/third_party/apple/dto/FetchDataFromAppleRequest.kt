package kr.motung_i.backend.global.third_party.apple.dto

data class FetchDataFromAppleRequest(
    val id: String,
    val token: String,
    val email: String,
)
