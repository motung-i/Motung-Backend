package kr.motung_i.backend.persistence.user.repository

import kr.motung_i.backend.persistence.user.entity.User
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
class JpaUserCustomRepositoryImpl(
    private val userRepository: UserRepository,
) : UserCustomRepository {
    override fun findByOauthId(oauthId: String): Optional<User> {
        return userRepository.findByOauthId(oauthId)
    }

    override fun save(user: User) {
        userRepository.save(user)
    }

}