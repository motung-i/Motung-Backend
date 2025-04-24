package kr.motung_i.backend.persistence.tour.repository.impl

import kr.motung_i.backend.persistence.tour.entity.Tour
import kr.motung_i.backend.persistence.user.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface TourJpaRepository: JpaRepository<Tour, UUID> {
    fun existsByUserAndGoalLocal(user: User, goalLocal: String): Boolean
    fun deleteByUserAndGoalLocal(user: User, goalLocal: String)
}