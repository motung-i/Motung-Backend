package kr.motung_i.backend.domain.tour.usecase

import kr.motung_i.backend.domain.tour.presentation.dto.response.FetchTourFilterRegionResponse
import kr.motung_i.backend.persistence.tour.entity.Country

interface FetchTourFilterRegionUsecase {
    fun execute(country: Country): FetchTourFilterRegionResponse
}