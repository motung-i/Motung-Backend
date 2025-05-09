package kr.motung_i.backend.domain.music.presentation.dto.response.impl

import kr.motung_i.backend.domain.music.presentation.dto.response.MusicResponse
import kr.motung_i.backend.persistence.music.entity.Music
import java.util.UUID

class PendingMusicResponse(
    val id: UUID?,

    override val title: String,

    override val singer: String,

    override val description: String,
): MusicResponse {
    companion object {
        fun from(music: Music): PendingMusicResponse = PendingMusicResponse(
            music.id,
            music.title,
            music.singer,
            music.description,
        )
    }
}