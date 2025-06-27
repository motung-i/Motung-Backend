package kr.motung_i.backend.domain.tour.usecase.impl

import kr.motung_i.backend.domain.tour.presentation.dto.response.FetchDrivingRouteResponse
import kr.motung_i.backend.domain.tour.presentation.dto.response.FetchDrivingRoutesResponse
import kr.motung_i.backend.domain.tour.usecase.FetchDrivingRouteUsecase
import kr.motung_i.backend.domain.user.usecase.FetchCurrentUserUsecase
import kr.motung_i.backend.global.exception.CustomException
import kr.motung_i.backend.global.exception.enums.CustomErrorCode
import kr.motung_i.backend.global.third_party.naver.NaverAdapter
import kr.motung_i.backend.persistence.tour.entity.Location
import kr.motung_i.backend.persistence.tour.repository.TourRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class FetchDriveRouteUsecaseImpl(
    private val fetchCurrentUserUsecase: FetchCurrentUserUsecase,
    private val naverAdapter: NaverAdapter,
    private val tourRepository: TourRepository,

): FetchDrivingRouteUsecase {
    override fun execute(startLat: Double, startLon: Double): FetchDrivingRoutesResponse {
        val currentUser = fetchCurrentUserUsecase.execute()
        val tour = tourRepository.findByUserAndIsActive(currentUser, true)
            ?: throw CustomException(CustomErrorCode.NOT_ACTIVATED_TOUR)

        val naverDirectionsResponse = naverAdapter.fetchDrivingRoute(
            startLocation = Location(startLat, startLon),
            endLocation = tour.location
        )

        return FetchDrivingRoutesResponse(
            traoptimal = FetchDrivingRouteResponse.fromNaverDistricts(naverDirectionsResponse.route.traoptimal.first()),
            traavoidtoll = FetchDrivingRouteResponse.fromNaverDistricts(naverDirectionsResponse.route.traavoidtoll.first()),
        )
    }
}