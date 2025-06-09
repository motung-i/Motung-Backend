package kr.motung_i.backend.persistence.tour.repository.impl

import kr.motung_i.backend.persistence.tour.entity.Tour
import kr.motung_i.backend.persistence.user.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.UUID

interface TourJpaRepository: JpaRepository<Tour, UUID> {
    fun findByUser(user: User): Tour?
    @Query("""
        SELECT t
        FROM Tour t
        JOIN FETCH t.tourLocation
        WHERE t.user = :user
    """)
    fun findWithTourLocationByUser(user: User): Tour?
    fun deleteByUser(user: User)
    fun existsByUser(user: User): Boolean
}