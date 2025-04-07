package kr.motung_i.backend.persistence.user.repository

import kr.motung_i.backend.persistence.user.entity.User

interface UserRepository {
    fun findByOauthId(oauthId: String): User?
    fun save(user: User)
}