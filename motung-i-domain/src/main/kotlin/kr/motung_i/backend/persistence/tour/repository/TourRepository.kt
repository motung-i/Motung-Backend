package kr.motung_i.backend.persistence.tour.repository

import kr.motung_i.backend.persistence.tour.entity.Tour
import kr.motung_i.backend.persistence.user.entity.User
import org.springframework.data.jpa.repository.Query

interface TourRepository {
    @Query("""
        SELECT t
        FROM Tour t
        JOIN FETCH t.tourLocation
        WHERE t.user = :user
    """)
    fun findWithTourLocationByUser(user: User): Tour?
    fun deleteByUser(user: User)
    fun save(tour: Tour)
    fun existsByUser(user: User): Boolean
}