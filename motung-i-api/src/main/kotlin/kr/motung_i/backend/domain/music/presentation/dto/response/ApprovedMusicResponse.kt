package kr.motung_i.backend.domain.music.presentation.dto.response

import kr.motung_i.backend.persistence.music.entity.Music

class ApprovedMusicResponse(
    override val title: String,

    override val singer: String,

    override val description: String,

    val rankNumber: Int?,
): MusicResponse {
    companion object {
        fun from(music: Music): ApprovedMusicResponse = ApprovedMusicResponse(
            music.title,
            music.singer,
            music.description,
            music.rankNumber,
        )
    }
}