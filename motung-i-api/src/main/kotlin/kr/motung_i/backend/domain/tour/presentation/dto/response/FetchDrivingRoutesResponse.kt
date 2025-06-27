package kr.motung_i.backend.domain.tour.presentation.dto.response

data class FetchDrivingRoutesResponse(
    val traavoidtoll: FetchDrivingRouteResponse,
    val traoptimal: FetchDrivingRouteResponse,
)