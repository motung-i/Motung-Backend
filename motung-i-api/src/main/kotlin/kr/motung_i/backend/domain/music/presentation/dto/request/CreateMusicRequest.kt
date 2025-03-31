package kr.motung_i.backend.domain.music.presentation.dto.request

import jakarta.validation.constraints.NotBlank

data class CreateMusicRequest(
    @NotBlank
    val title: String,

    @NotBlank
    val singer: String,

    @NotBlank
    val description: String
)
