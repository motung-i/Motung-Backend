package kr.motung_i.backend.domain.music.presentation.dto.request

data class UpdateMusicRequest(
    val title: String?,

    val singer: String?,

    val description: String?,
)
