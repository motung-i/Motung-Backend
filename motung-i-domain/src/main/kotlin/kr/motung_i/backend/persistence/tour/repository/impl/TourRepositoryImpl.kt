package kr.motung_i.backend.persistence.tour.repository.impl

import kr.motung_i.backend.persistence.tour.repository.TourRepository
import kr.motung_i.backend.persistence.user.entity.User

class TourRepositoryImpl(
    private val tourJpaRepository: TourJpaRepository,
): TourRepository {
    override fun existsByUserAndGoalLocal(user: User, local: String): Boolean =
        tourJpaRepository.existsByUserAndGoalLocal(user, local)
}