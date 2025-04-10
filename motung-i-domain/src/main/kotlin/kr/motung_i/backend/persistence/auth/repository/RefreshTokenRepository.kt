package kr.motung_i.backend.persistence.auth.repository

import kr.motung_i.backend.persistence.auth.entity.RefreshToken
import org.springframework.data.repository.CrudRepository
import java.util.Optional

interface RefreshTokenRepository : CrudRepository<RefreshToken, String> {
    fun findByRefreshToken(refreshToken: String): Optional<RefreshToken>
}
