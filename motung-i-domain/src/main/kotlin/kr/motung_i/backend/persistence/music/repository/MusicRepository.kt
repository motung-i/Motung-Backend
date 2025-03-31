package kr.motung_i.backend.persistence.music.repository

import kr.motung_i.backend.persistence.music.entity.Music
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface MusicRepository : JpaRepository<Music, UUID> {
}