package kr.motung_i.backend.persistence.auth.repository.impl

import kr.motung_i.backend.persistence.auth.entity.RefreshToken
import kr.motung_i.backend.persistence.auth.repository.RefreshTokenRepository
import org.springframework.stereotype.Repository

@Repository
class RefreshTokenRepositoryImpl(
    private val refreshTokenJpaRepository: RefreshTokenJpaRepository,
) : RefreshTokenRepository {
    override fun save(refreshToken: RefreshToken) {
        refreshTokenJpaRepository.save(refreshToken)
    }

}