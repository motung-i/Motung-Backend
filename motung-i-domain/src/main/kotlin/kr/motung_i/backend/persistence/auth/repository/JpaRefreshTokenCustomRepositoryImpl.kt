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

    override fun delete(refreshToken: String) {
        refreshTokenRepository.deleteById(
            refreshToken,
        )
    }

    override fun find(
        refreshToken: String,
        clientId: String,
    ): RefreshToken {
        val result: RefreshToken = refreshTokenRepository.findRefreshToken(refreshToken).orElseThrow()
        if (result.refreshToken != clientId) throw IllegalArgumentException("잘못된 요청입니다.")
        return result
    }
}
