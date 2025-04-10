package kr.motung_i.backend.persistence.user.repository

import kr.motung_i.backend.persistence.user.entity.User
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class JpaUserCustomRepositoryImpl(
    private val userRepository: UserRepository,
) : UserCustomRepository {
    override fun findByOauthId(oauthId: String): User? = userRepository.findByOauthId(oauthId)

    override fun save(user: User): User = userRepository.save(user)

    override fun findByUserId(userId: UUID): Optional<User> = userRepository.findByUserId(userId = userId)
}
