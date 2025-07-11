package kr.motung_i.backend.persistence.tour.repository.impl

import kr.motung_i.backend.persistence.tour.entity.Tour
import kr.motung_i.backend.persistence.tour.repository.TourRepository
import kr.motung_i.backend.persistence.user.entity.User
import org.springframework.stereotype.Repository

@Repository
class TourRepositoryImpl(
    private val tourJpaRepository: TourJpaRepository,
): TourRepository {
    override fun findByUser(user: User): Tour? =
        tourJpaRepository.findByUser(user)

    override fun findByUserAndIsActive(
        user: User,
        isActive: Boolean
    ): Tour? =
        tourJpaRepository.findByUserAndIsActive(user, isActive)

    override fun delete(tour: Tour) {
        tourJpaRepository.delete(tour)
    }

    override fun save(tour: Tour) {
        tourJpaRepository.save(tour)
    }
}