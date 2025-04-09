package kr.motung_i.backend.domain.auth.presentation.dto.response.impl

import jakarta.validation.constraints.NotBlank

data class RegisterRequest(
    @field:NotBlank
    val name: String,
)
