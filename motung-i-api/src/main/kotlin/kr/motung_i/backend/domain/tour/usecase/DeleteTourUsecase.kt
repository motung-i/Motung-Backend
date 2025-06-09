package kr.motung_i.backend.domain.tour.usecase

import kr.motung_i.backend.domain.user.usecase.FetchCurrentUserUsecase
import kr.motung_i.backend.global.exception.CustomException
import kr.motung_i.backend.global.exception.enums.CustomErrorCode
import kr.motung_i.backend.persistence.tour.repository.TourRepository
import kr.motung_i.backend.persistence.tour_location.repository.TourLocationRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class DeleteTourUsecase(
    private val tourRepository: TourRepository,
    private val tourLocationRepository: TourLocationRepository,
    private val fetchCurrentUserUsecase: FetchCurrentUserUsecase,
) {
    fun execute() {
        val currentUser = fetchCurrentUserUsecase.execute()
        val tour = tourRepository.findWithTourLocationByUser(currentUser)
            ?: throw CustomException(CustomErrorCode.NOT_FOUND_TOUR)

        tourRepository.delete(tour)
        tourLocationRepository.delete(tour.tourLocation)
    }
}