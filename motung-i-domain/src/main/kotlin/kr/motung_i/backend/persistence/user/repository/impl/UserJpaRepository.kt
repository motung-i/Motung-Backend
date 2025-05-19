package kr.motung_i.backend.persistence.user.repository.impl

import kr.motung_i.backend.persistence.user.entity.User
import kr.motung_i.backend.persistence.user.entity.enums.Provider
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface UserJpaRepository : JpaRepository<User, UUID> {
    fun findByOauthId(oauthId: String): User?

    fun findByEmail(email: String): User?

    fun existsByNickname(name: String): Boolean

    fun existsByOauthIdAndProvider(oauthId: String, provider: Provider): Boolean

    fun findByOauthIdAndProvider(oauthId: String, provider: Provider): User?
}
