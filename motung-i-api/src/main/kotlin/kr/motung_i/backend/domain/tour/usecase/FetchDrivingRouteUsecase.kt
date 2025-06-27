package kr.motung_i.backend.domain.tour.usecase

import kr.motung_i.backend.domain.tour.presentation.dto.response.FetchDrivingRoutesResponse

interface FetchDrivingRouteUsecase {
    fun execute(startLat: Double, startLon: Double): FetchDrivingRoutesResponse
}