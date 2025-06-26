package kr.motung_i.backend.domain.tour.usecase

import kr.motung_i.backend.domain.tour.presentation.dto.response.FetchTourFilterDistrictResponse
import kr.motung_i.backend.persistence.tour.entity.Country

interface FetchTourFilterDistrictUsecase {
    fun execute(country: Country, region: String): FetchTourFilterDistrictResponse
}