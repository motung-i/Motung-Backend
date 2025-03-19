package kr.motung_i.backend.domain.user.repository

import kr.motung_i.backend.domain.user.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional
import java.util.UUID

interface UserRepository : JpaRepository<User, UUID> {
    fun findByEmail(email: String): Optional<User>
}
