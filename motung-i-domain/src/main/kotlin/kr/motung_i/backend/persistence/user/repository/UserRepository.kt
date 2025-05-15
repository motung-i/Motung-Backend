package kr.motung_i.backend.persistence.user.repository

import kr.motung_i.backend.persistence.user.entity.User
import java.util.*

interface UserRepository {
    fun findByOauthId(oauthId: String): User?

    fun save(user: User): User

    fun findByUserId(userId: UUID): Optional<User>

    fun existsByNickname(name: String): Boolean
}
