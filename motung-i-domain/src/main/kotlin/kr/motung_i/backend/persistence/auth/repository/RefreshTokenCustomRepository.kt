package kr.motung_i.backend.persistence.auth.repository

import kr.motung_i.backend.persistence.auth.entity.RefreshToken
import java.util.*

interface RefreshTokenCustomRepository {
    fun save(refreshToken: RefreshToken)

    fun delete(refreshToken: String)

    fun find(refreshToken: String): Optional<RefreshToken>
}
