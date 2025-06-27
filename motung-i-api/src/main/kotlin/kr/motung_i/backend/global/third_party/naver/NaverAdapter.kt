package kr.motung_i.backend.global.third_party.naver

import kr.motung_i.backend.global.third_party.naver.dto.NaverDirectionsResponse
import kr.motung_i.backend.global.third_party.naver.feign.NaverDirectionsFeignClient
import kr.motung_i.backend.persistence.tour.entity.Location
import org.springframework.stereotype.Service

@Service
class NaverAdapter(
    private val naverDirectionsFeignClient: NaverDirectionsFeignClient,
) {
    fun fetchDrivingRoute(startLocation: Location, endLocation: Location): NaverDirectionsResponse {
        return naverDirectionsFeignClient.fetchDirections(
            start = "${startLocation.lon},${startLocation.lat}",
            goal = "${endLocation.lon},${endLocation.lat}",
        )
    }
}