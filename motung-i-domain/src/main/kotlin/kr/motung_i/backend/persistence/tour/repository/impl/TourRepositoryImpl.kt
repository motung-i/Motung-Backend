package kr.motung_i.backend.persistence.tour.repository.impl

import kr.motung_i.backend.persistence.tour.repository.TourRepository
import kr.motung_i.backend.persistence.user.entity.User
import org.springframework.stereotype.Repository

@Repository
class TourRepositoryImpl(
    private val tourJpaRepository: TourJpaRepository,
): TourRepository {
    override fun existsByUserAndGoalLocal(user: User, goalLocal: String): Boolean =
        tourJpaRepository.existsByUserAndGoalLocal(user, goalLocal)

    override fun deleteByUserAndGoalLocal(user: User, goalLocal: String) {
        tourJpaRepository.deleteByUserAndGoalLocal(user, goalLocal)
    }
}