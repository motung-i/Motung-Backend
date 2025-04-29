package kr.motung_i.backend.persistence.user_suspension.repository.impl

import kr.motung_i.backend.persistence.user.entity.User
import kr.motung_i.backend.persistence.user_suspension.entity.UserSuspension
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface UserSuspensionJpaRepository : JpaRepository<UserSuspension, Long> {
    @Query(
        """
        SELECT us 
        FROM UserSuspension us LEFT JOIN FETCH us.reasons
        WHERE us.user = :user
    """
    )
    fun findWithReasonsByUser(user: User): UserSuspension?
}