package kr.motung_i.backend.persistence.auth.repository.impl

import kr.motung_i.backend.persistence.auth.entity.RefreshToken
import kr.motung_i.backend.persistence.auth.repository.RefreshTokenCustomRepository
import kr.motung_i.backend.persistence.auth.repository.RefreshTokenRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class JpaRefreshTokenCustomRepositoryImpl(
    private val refreshTokenRepository: RefreshTokenRepository,
) : RefreshTokenCustomRepository {
    override fun save(refreshToken: RefreshToken) {
        refreshTokenRepository.save(refreshToken)
    }

    override fun delete(refreshToken: String) {
        refreshTokenRepository.deleteById(
            refreshToken,
        )
    }

    override fun find(refreshToken: String): Optional<RefreshToken> = refreshTokenRepository.findByRefreshToken(refreshToken)
}
