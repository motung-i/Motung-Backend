package kr.motung_i.backend.persistence.user_suspension.repository.impl

import kr.motung_i.backend.persistence.user.entity.User
import kr.motung_i.backend.persistence.user_suspension.entity.UserSuspension
import kr.motung_i.backend.persistence.user_suspension.repository.UserSuspensionRepository
import org.springframework.stereotype.Repository

@Repository
class UserSuspensionRepositoryImpl(
    private val userSuspensionJpaRepository: UserSuspensionJpaRepository,
): UserSuspensionRepository {
    override fun save(userSuspension: UserSuspension) {
        userSuspensionJpaRepository.save(userSuspension)
    }

    override fun findWithReasonsByUser(user: User): UserSuspension? =
        userSuspensionJpaRepository.findWithReasonsByUser(user)

    override fun delete(userSuspension: UserSuspension) {
        userSuspensionJpaRepository.delete(userSuspension)
    }

}