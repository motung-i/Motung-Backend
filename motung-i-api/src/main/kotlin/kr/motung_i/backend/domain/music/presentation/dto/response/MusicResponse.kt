package kr.motung_i.backend.domain.music.presentation.dto.response

import kr.motung_i.backend.persistence.music.entity.Music

class MusicResponse(
    val title: String,

    val singer: String,

    val description: String,
) {
    companion object {
        fun from(music: Music): MusicResponse = MusicResponse(
            music.title,
            music.singer,
            music.description,
        )
    }
}