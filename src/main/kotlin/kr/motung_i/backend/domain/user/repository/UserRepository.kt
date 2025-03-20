package kr.motung_i.backend.domain.user.repository

import kr.motung_i.backend.domain.user.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional
import java.util.UUID

interface UserRepository : JpaRepository<User, UUID> {
<<<<<<< HEAD
    fun findByOauthId(oauthId: String): Optional<User>
=======
    fun findByEmail(email: String): Optional<User>
>>>>>>> origin/feature/#1-create-oauth2-login
}
