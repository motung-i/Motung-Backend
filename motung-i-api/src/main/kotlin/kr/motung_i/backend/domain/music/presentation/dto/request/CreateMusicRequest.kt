package kr.motung_i.backend.domain.music.presentation.dto.request

import jakarta.validation.constraints.NotBlank

data class CreateMusicRequest(
    @field:NotBlank
    val title: String,

    @field:NotBlank
    val singer: String,

    @field:NotBlank
    val description: String
)
