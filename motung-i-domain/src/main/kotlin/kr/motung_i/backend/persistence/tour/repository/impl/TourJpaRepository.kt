package kr.motung_i.backend.persistence.tour.repository.impl

import kr.motung_i.backend.persistence.tour.entity.Tour
import kr.motung_i.backend.persistence.user.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface TourJpaRepository: JpaRepository<Tour, UUID> {
    fun findByUser(user: User): Tour?
    fun findByUserAndIsActive(user: User, isActive: Boolean): Tour?
    fun existsByUserAndIsActive(user: User, isActive: Boolean): Boolean
}