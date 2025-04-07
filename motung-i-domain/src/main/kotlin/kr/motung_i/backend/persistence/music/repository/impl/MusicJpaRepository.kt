package kr.motung_i.backend.persistence.music.repository.impl

import kr.motung_i.backend.persistence.music.entity.Music
import kr.motung_i.backend.persistence.music.entity.enums.MusicStatus
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface MusicJpaRepository : JpaRepository<Music, UUID> {
    fun findByMusicStatusOrderByRankNumber(musicStatus: MusicStatus): List<Music>
    fun findByRankNumber(rankNumber: Int): Music?
}