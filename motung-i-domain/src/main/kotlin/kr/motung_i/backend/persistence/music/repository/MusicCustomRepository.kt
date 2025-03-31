package kr.motung_i.backend.persistence.music.repository

import kr.motung_i.backend.persistence.music.entity.Music

interface MusicCustomRepository {
    fun save(music: Music)
}