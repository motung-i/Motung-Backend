package kr.motung_i.backend.persistence.tour.repository

import kr.motung_i.backend.persistence.tour.entity.Tour
import kr.motung_i.backend.persistence.user.entity.User

interface TourRepository {
    fun findByUser(user: User): Tour?
    fun findByUserAndIsActive(user: User, isActive: Boolean): Tour?
    fun delete(tour: Tour)
    fun save(tour: Tour)
}