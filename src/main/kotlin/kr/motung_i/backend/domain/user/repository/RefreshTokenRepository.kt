package kr.motung_i.backend.domain.user.repository

import kr.motung_i.backend.domain.user.entity.RefreshToken
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface RefreshTokenRepository : CrudRepository<RefreshToken, String>
