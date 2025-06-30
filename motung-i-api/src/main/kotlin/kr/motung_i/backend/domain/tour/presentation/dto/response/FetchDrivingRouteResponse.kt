package kr.motung_i.backend.domain.tour.presentation.dto.response

import kr.motung_i.backend.global.third_party.naver.dto.NaverDirectionsResponse

data class FetchDrivingRouteResponse(
    val startLat: Double,
    val startLon: Double,
    val endLat: Double,
    val endLon: Double,
    val distance: Int,
    val duration: Int,
    val taxiFare: Int,
    val tollFare: Int,
    val guide: List<String>,
    val section: List<Section>,
    val path: List<List<Double>>,
) {

    data class Section(
        val pointIndex: Int,
        val pointCount: Int,
        val congestion: Int,
    )

    companion object {
        fun fromNaverDistricts(
            naverDirection: NaverDirectionsResponse.Direction
        ): FetchDrivingRouteResponse {
            return FetchDrivingRouteResponse(
                startLat = naverDirection.summary.start.location[1],
                startLon = naverDirection.summary.start.location[0],
                endLat = naverDirection.summary.goal.location[1],
                endLon = naverDirection.summary.goal.location[0],
                distance = naverDirection.summary.distance,
                duration = naverDirection.summary.duration,
                taxiFare = naverDirection.summary.taxiFare,
                tollFare = naverDirection.summary.tollFare,
                guide = naverDirection.guide.map { it.instructions },
                section = naverDirection.section.map { Section(it.pointIndex, it.pointCount, it.congestion) },
                path = naverDirection.path,
            )
        }
    }
}