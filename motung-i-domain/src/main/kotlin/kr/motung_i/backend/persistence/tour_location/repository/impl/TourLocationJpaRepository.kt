package kr.motung_i.backend.persistence.tour_location.repository.impl

import kr.motung_i.backend.persistence.tour_location.entity.TourLocation
import kr.motung_i.backend.persistence.user.entity.User
import org.springframework.data.jpa.repository.JpaRepository

interface TourLocationJpaRepository: JpaRepository<TourLocation, Long> {
    fun findByUser(user: User): TourLocation?
}
