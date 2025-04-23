package kr.motung_i.backend.persistence.tour.repository

import kr.motung_i.backend.persistence.user.entity.User

interface TourRepository {
    fun existsByUserAndGoalLocal(user: User, local: String): Boolean
}