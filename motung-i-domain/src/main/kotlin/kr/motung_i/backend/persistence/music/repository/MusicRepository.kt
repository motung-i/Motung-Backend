package kr.motung_i.backend.persistence.music.repository

import kr.motung_i.backend.persistence.music.entity.Music
import kr.motung_i.backend.persistence.music.entity.enums.MusicStatus
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface MusicRepository : JpaRepository<Music, UUID> {
    fun findByMusicStatusOrderByRankNumber(musicStatus: MusicStatus): List<Music>
    fun findByRankNumber(rankNumber: Int): Music?
}