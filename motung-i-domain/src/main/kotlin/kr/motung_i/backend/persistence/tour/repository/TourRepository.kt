package kr.motung_i.backend.persistence.tour.repository

import kr.motung_i.backend.persistence.tour.entity.Tour
import kr.motung_i.backend.persistence.user.entity.User

interface TourRepository {
    fun findByUser(user: User): Tour?
    fun deleteByUser(user: User)
    fun save(tour: Tour)
    fun existsByUser(user: User): Boolean
}