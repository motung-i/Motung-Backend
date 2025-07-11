package kr.motung_i.backend.domain.tour.usecase.impl

import kr.motung_i.backend.domain.tour.usecase.CreateTourCommentUsecase
import kr.motung_i.backend.domain.tour.usecase.CreateTourLocationUsecase
import kr.motung_i.backend.domain.tour.usecase.dto.GeoLocation
import kr.motung_i.backend.domain.user.usecase.FetchCurrentUserUsecase
import kr.motung_i.backend.global.exception.CustomException
import kr.motung_i.backend.global.exception.enums.CustomErrorCode
import kr.motung_i.backend.persistence.tour.repository.TourRepository
import kr.motung_i.backend.persistence.tour.entity.Local
import kr.motung_i.backend.persistence.tour.entity.Location
import kr.motung_i.backend.persistence.tour.entity.Tour
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class CreateTourLocationUsecaseImpl(
    val fetchCurrentUserUsecase: FetchCurrentUserUsecase,
    val tourRepository: TourRepository,
): CreateTourLocationUsecase {
    override fun execute(local: Local, geoLocation: GeoLocation) {
        val currentUser = fetchCurrentUserUsecase.execute()
        val tour = tourRepository.findByUser(currentUser)

        if (tour == null) {
            tourRepository.save(
                Tour(
                    user = fetchCurrentUserUsecase.execute(),
                    local = local,
                    location = Location(
                        lat = geoLocation.lat,
                        lon = geoLocation.lon,
                    ),
                )
            )
            return
        }

        if (tour.isActive) {
            throw CustomException(CustomErrorCode.ALREADY_EXISTS_TOUR)
        }

        tourRepository.save(
            tour.copy(
                local = local,
                location = Location(
                    lat = geoLocation.lat,
                    lon = geoLocation.lon,
                )
            )
        )
    }
}