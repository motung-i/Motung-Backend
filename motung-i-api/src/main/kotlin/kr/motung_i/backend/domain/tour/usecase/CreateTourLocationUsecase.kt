package kr.motung_i.backend.domain.tour.usecase

import kr.motung_i.backend.domain.tour.usecase.dto.GeoLocation
import kr.motung_i.backend.domain.user.usecase.FetchCurrentUserUsecase
import kr.motung_i.backend.global.exception.CustomException
import kr.motung_i.backend.global.exception.enums.CustomErrorCode
import kr.motung_i.backend.persistence.tour.repository.TourRepository
import kr.motung_i.backend.persistence.tour_location.entity.Local
import kr.motung_i.backend.persistence.tour_location.entity.Location
import kr.motung_i.backend.persistence.tour_location.entity.TourLocation
import kr.motung_i.backend.persistence.tour_location.repository.TourLocationRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class CreateTourLocationUsecase(
    val tourLocationRepository: TourLocationRepository,
    val fetchCurrentUserUsecase: FetchCurrentUserUsecase,
    val tourRepository: TourRepository,
) {
    fun execute(local: Local, geoLocation: GeoLocation) {
        val currentUser = fetchCurrentUserUsecase.execute()

        if (tourRepository.existsByUser(currentUser)) {
            throw CustomException(CustomErrorCode.ALREADY_EXISTS_TOUR)
        }

        val tourLocation = tourLocationRepository.findByUser(currentUser)

        if (tourLocation == null) {
            tourLocationRepository.save(
                TourLocation(
                    user = fetchCurrentUserUsecase.execute(),
                    local = local,
                    location = Location(
                        lat = geoLocation.lat,
                        lon = geoLocation.lon,
                    )
                )
            )
            return
        }

        tourLocationRepository.save(
            tourLocation.copy(
                local = local,
                location = Location(
                    lat = geoLocation.lat,
                    lon = geoLocation.lon,
                )
            )
        )
    }
}