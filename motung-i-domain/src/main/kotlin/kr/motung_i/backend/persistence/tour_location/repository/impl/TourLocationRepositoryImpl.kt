package kr.motung_i.backend.persistence.tour_location.repository.impl

import kr.motung_i.backend.persistence.tour_location.entity.TourLocation
import kr.motung_i.backend.persistence.tour_location.repository.TourLocationRepository
import kr.motung_i.backend.persistence.user.entity.User
import org.springframework.stereotype.Repository

@Repository
class TourLocationRepositoryImpl(
    private val tourLocationJpaRepository : TourLocationJpaRepository,
): TourLocationRepository {
    override fun save(tourLocation: TourLocation) {
        tourLocationJpaRepository.save(tourLocation)
    }

    override fun findByUser(user: User): TourLocation? =
        tourLocationJpaRepository.findByUser(user)
}