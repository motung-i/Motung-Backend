package kr.motung_i.backend.domain.music.presentation.dto.response

import kr.motung_i.backend.persistence.music.entity.Music

data class MusicListResponse(
    val musicList: List<MusicResponse>
) {
    companion object {
        fun from(musicList: List<Music>): MusicListResponse =
            MusicListResponse(musicList.map { MusicResponse.from(it) })
    }
}