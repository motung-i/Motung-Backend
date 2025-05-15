package kr.motung_i.backend.persistence.user.repository.impl

import kr.motung_i.backend.persistence.user.entity.User
import kr.motung_i.backend.persistence.user.repository.UserRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class UserRepositoryImpl(
    private val userJpaRepository: UserJpaRepository,
) : UserRepository {
    override fun findByOauthId(oauthId: String): User? = userJpaRepository.findByOauthId(oauthId)

    override fun save(user: User): User = userJpaRepository.save(user)

    override fun findByUserId(userId: UUID): Optional<User> = userJpaRepository.findById(userId)

    override fun existsByNickname(name: String): Boolean =
        userJpaRepository.existsByNickname(name)
}
