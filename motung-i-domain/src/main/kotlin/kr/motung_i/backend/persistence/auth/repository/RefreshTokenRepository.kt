package kr.motung_i.backend.persistence.auth.repository

import kr.motung_i.backend.persistence.auth.entity.RefreshToken

interface RefreshTokenRepository {
    fun save(refreshToken: RefreshToken)
}