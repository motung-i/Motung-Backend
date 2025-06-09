package kr.motung_i.backend.persistence.tour.repository

import kr.motung_i.backend.persistence.tour.entity.Tour
import kr.motung_i.backend.persistence.user.entity.User
import org.springframework.data.jpa.repository.Query

interface TourRepository {
    fun findByUser(user: User): Tour?
    fun findWithTourLocationByUser(user: User): Tour?
    fun delete(tour: Tour)
    fun save(tour: Tour)
    fun existsByUser(user: User): Boolean
}