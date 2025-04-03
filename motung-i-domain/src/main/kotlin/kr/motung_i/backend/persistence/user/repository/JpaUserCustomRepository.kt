package kr.motung_i.backend.persistence.user.repository

import kr.motung_i.backend.persistence.user.entity.User
import org.springframework.stereotype.Repository

@Repository
class JpaUserCustomRepository(
    private val userRepository: UserRepository,
) : UserCustomRepository {
    override fun findByOauthId(oauthId: String): User? {
        return userRepository.findByOauthId(oauthId)
    }

    override fun save(user: User) {
        userRepository.save(user)
    }

}