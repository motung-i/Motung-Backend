package kr.motung_i.backend.domain.travel_info.usecase

import kr.motung_i.backend.domain.travel_info.presentation.dto.response.TravelInfoListResponse

interface FetchAllTravelInfoUsecase {
    fun execute(): TravelInfoListResponse
}