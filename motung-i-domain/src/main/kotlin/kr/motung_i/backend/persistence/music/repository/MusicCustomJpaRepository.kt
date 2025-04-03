package kr.motung_i.backend.persistence.music.repository

import kr.motung_i.backend.persistence.music.entity.Music
import kr.motung_i.backend.persistence.music.entity.enums.MusicStatus
import org.springframework.stereotype.Repository
import java.util.*
import kotlin.jvm.optionals.getOrNull

@Repository
class MusicCustomJpaRepository(
    private val musicRepository: MusicRepository,
) : MusicCustomRepository {
    override fun save(music: Music) {
        musicRepository.save(music)
    }

    override fun findById(id: UUID): Music? =
        musicRepository.findById(id).getOrNull()

    override fun delete(music: Music) {
        musicRepository.delete(music)
    }

    override fun findByMusicStatusOrderByRankNumber(musicStatus: MusicStatus): List<Music> =
        musicRepository.findByMusicStatusOrderByRankNumber(musicStatus)

    override fun findByRankNumber(rankNumber: Int): Music? =
        musicRepository.findByRankNumber(rankNumber)
}