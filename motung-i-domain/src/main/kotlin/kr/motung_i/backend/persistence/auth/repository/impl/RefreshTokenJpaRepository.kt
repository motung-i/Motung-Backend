package kr.motung_i.backend.persistence.auth.repository.impl

import kr.motung_i.backend.persistence.auth.entity.RefreshToken
import org.springframework.data.repository.CrudRepository

interface RefreshTokenJpaRepository : CrudRepository<RefreshToken, String>
