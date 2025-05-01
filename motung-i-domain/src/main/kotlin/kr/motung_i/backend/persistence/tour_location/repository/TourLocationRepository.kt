package kr.motung_i.backend.persistence.tour_location.repository

import kr.motung_i.backend.persistence.tour_location.entity.TourLocation
import kr.motung_i.backend.persistence.user.entity.User

interface TourLocationRepository {

    fun save(tourLocation: TourLocation)
    fun findByUser(user: User): TourLocation?
}