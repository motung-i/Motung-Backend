package kr.motung_i.backend.persistence.tour.repository.impl

import kr.motung_i.backend.persistence.tour.entity.Tour
import kr.motung_i.backend.persistence.tour.repository.TourRepository
import kr.motung_i.backend.persistence.user.entity.User
import org.springframework.stereotype.Repository

@Repository
class TourRepositoryImpl(
    private val tourJpaRepository: TourJpaRepository,
): TourRepository {
    override fun findWithTourLocationByUser(user: User): Tour? =
        tourJpaRepository.findByUser(user)

    override fun deleteByUser(user: User) {
        tourJpaRepository.deleteByUser(user)
    }

    override fun save(tour: Tour) {
        tourJpaRepository.save(tour)
    }

    override fun existsByUser(user: User): Boolean =
        tourJpaRepository.existsByUser(user)
}