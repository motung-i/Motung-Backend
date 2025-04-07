package kr.motung_i.backend.persistence.user.repository.impl

import kr.motung_i.backend.persistence.user.entity.User
import kr.motung_i.backend.persistence.user.repository.UserRepository
import org.springframework.stereotype.Repository

@Repository
class UserRepositoryImpl(
    private val userJpaRepository: UserJpaRepository,
) : UserRepository {
    override fun findByOauthId(oauthId: String): User? {
        return userJpaRepository.findByOauthId(oauthId)
    }

    override fun save(user: User) {
        userJpaRepository.save(user)
    }

}