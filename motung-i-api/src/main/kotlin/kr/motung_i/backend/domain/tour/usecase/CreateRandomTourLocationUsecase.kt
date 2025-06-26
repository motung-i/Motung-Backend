package kr.motung_i.backend.domain.tour.usecase

import kr.motung_i.backend.domain.tour.presentation.dto.response.FetchRandomTourLocationResponse
import kr.motung_i.backend.persistence.tour.entity.Country

interface CreateRandomTourLocationUsecase {
    fun execute(
        country: Country,
        regions: List<String>,
        districts: List<String>
    ): FetchRandomTourLocationResponse
}