package kr.motung_i.backend.domain.music.presentation.dto.request

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern

data class CreateMusicRequest(
    @field:NotBlank
    val title: String,

    @field:NotBlank
    val singer: String,

    @field:Pattern(
        regexp = """^(https?:\/\/)?(www\.)?(youtube\.com|youtube-nocookie\.com|youtu\.be)\/(watch\?v=|embed\/|v\/)?([\w\-]+)(\S+)?$""",
        message = "유효한 유튜브 영상 URL을 입력해주세요.",
    )
    val youtubeUrl: String,

    @field:NotBlank
    val description: String
)
