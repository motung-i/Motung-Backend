package kr.motung_i.backend.domain.music.presentation.dto.response.impl

import kr.motung_i.backend.domain.music.presentation.dto.response.MusicResponse
import kr.motung_i.backend.persistence.music.entity.Music

class ApprovedMusicResponse(
    override val title: String,

    override val singer: String,

    override val description: String,

    override val youtubeUrl: String,

    override val thumbnailUrl: String,

    val rankNumber: Int?,
): MusicResponse {
    companion object {
        fun from(music: Music): ApprovedMusicResponse = ApprovedMusicResponse(
            title = music.title,
            singer = music.singer,
            description = music.description,
            youtubeUrl = music.youtubeUrl,
            thumbnailUrl = music.thumbnailUrl,
            rankNumber = music.rankNumber,
        )
    }
}