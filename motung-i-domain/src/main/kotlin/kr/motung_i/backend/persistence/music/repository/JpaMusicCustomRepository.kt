package kr.motung_i.backend.persistence.music.repository

import kr.motung_i.backend.persistence.music.entity.Music
import org.springframework.stereotype.Repository

@Repository
class JpaMusicCustomRepository(
    private val musicRepository: MusicRepository,
) : MusicCustomRepository {
    override fun save(music: Music) {
        musicRepository.save(music)
    }
}