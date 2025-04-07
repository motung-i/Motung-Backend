package kr.motung_i.backend.persistence.music.repository.impl

import kr.motung_i.backend.persistence.music.entity.Music
import kr.motung_i.backend.persistence.music.entity.enums.MusicStatus
import kr.motung_i.backend.persistence.music.repository.MusicRepository
import org.springframework.stereotype.Repository
import java.util.*
import kotlin.jvm.optionals.getOrNull

@Repository
class MusicRepositoryImpl(
    private val musicJpaRepository: MusicJpaRepository,
) : MusicRepository {
    override fun save(music: Music) {
        musicJpaRepository.save(music)
    }

    override fun findById(id: UUID): Music? =
        musicJpaRepository.findById(id).getOrNull()

    override fun delete(music: Music) {
        musicJpaRepository.delete(music)
    }

    override fun findByMusicStatusOrderByRankNumber(musicStatus: MusicStatus): List<Music> =
        musicJpaRepository.findByMusicStatusOrderByRankNumber(musicStatus)

    override fun findByRankNumber(rankNumber: Int): Music? =
        musicJpaRepository.findByRankNumber(rankNumber)
}