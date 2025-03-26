package kr.motung_i.backend.persistence.auth.repository

import kr.motung_i.backend.persistence.auth.entity.RefreshToken
import org.springframework.stereotype.Repository

@Repository
class JpaRefreshTokenCustomRepositoryImpl(
    private val refreshTokenRepository: RefreshTokenRepository,
) : RefreshTokenCustomRepository {
    override fun save(refreshToken: RefreshToken) {
        refreshTokenRepository.save(refreshToken)
    }

}