package kr.motung_i.backend.persistence.user.repository

import kr.motung_i.backend.persistence.user.entity.User
import java.util.Optional
import java.util.UUID

interface UserCustomRepository {
    fun findByOauthId(oauthId: String): Optional<User>

    fun save(user: User): User

    fun findByUserId(clientId: UUID): Optional<User>
}
