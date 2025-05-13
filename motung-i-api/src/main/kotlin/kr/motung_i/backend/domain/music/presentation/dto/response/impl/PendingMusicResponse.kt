package kr.motung_i.backend.domain.music.presentation.dto.response.impl

import kr.motung_i.backend.domain.music.presentation.dto.response.MusicResponse
import kr.motung_i.backend.persistence.music.entity.Music
import java.util.UUID

class PendingMusicResponse(
    val id: UUID?,

    override val title: String,

    override val singer: String,

    override val description: String,

    override val youtubeUrl: String,

    override val thumbnailUrl: String,
): MusicResponse {
    companion object {
        fun from(music: Music): PendingMusicResponse = PendingMusicResponse(
            id = music.id,
            title = music.title,
            singer = music.singer,
            description = music.description,
            youtubeUrl = music.youtubeUrl,
            thumbnailUrl = music.thumbnailUrl,
        )
    }
}