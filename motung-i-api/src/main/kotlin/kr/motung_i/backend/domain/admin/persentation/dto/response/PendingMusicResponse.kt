package kr.motung_i.backend.domain.admin.persentation.dto.response

import kr.motung_i.backend.domain.music.presentation.dto.response.MusicResponse
import kr.motung_i.backend.persistence.music.entity.Music
import java.util.UUID

class PendingMusicResponse(
    val musicId: UUID?,

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