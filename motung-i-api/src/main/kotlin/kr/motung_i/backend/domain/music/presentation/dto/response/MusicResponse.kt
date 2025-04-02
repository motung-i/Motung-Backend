package kr.motung_i.backend.domain.music.presentation.dto.response

import kr.motung_i.backend.domain.music.presentation.dto.response.impl.ApprovedMusicResponse
import kr.motung_i.backend.domain.music.presentation.dto.response.impl.PendingMusicResponse
import kr.motung_i.backend.persistence.music.entity.Music
import kr.motung_i.backend.persistence.music.entity.enums.MusicStatus

interface MusicResponse {
    val title: String
    val singer: String
    val description: String

    companion object {
        fun from(music: Music): MusicResponse =
            when (music.musicStatus) {
                MusicStatus.PENDING -> PendingMusicResponse.from(music)
                MusicStatus.APPROVED -> ApprovedMusicResponse.from(music)
            }
    }
}