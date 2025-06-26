package kr.motung_i.backend.domain.tour.usecase

import kr.motung_i.backend.domain.tour.usecase.dto.GeoLocation
import kr.motung_i.backend.persistence.tour.entity.Local

interface CreateTourLocationUsecase {
    fun execute(local: Local, geoLocation: GeoLocation)
}