package kr.motung_i.backend.domain.travel_info.presentation.dto.response

import kr.motung_i.backend.persistence.travel_info.entity.TravelInfo

data class TravelInfoListResponse(
    val travelInfoList: List<TravelInfoResponse>,
) {
    companion object {
        fun from(travelInfoList: List<TravelInfo>): TravelInfoListResponse =
            TravelInfoListResponse(travelInfoList.map { TravelInfoResponse.from(it) })
    }
}