package kr.motung_i.backend.persistence.music.repository

import kr.motung_i.backend.persistence.music.entity.Music
import kr.motung_i.backend.persistence.music.entity.enums.MusicStatus
import java.util.UUID

interface MusicCustomRepository {
    fun save(music: Music)
    fun findById(id: UUID): Music?
    fun delete(music: Music)
    fun findByMusicStatusOrderByRankNumber(musicStatus: MusicStatus): List<Music>
    fun findByRankNumber(rankNumber: Int): Music?
}