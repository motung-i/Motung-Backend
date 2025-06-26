package kr.motung_i.backend.domain.tour.usecase

import kr.motung_i.backend.domain.tour.presentation.dto.response.FetchTourLocationMyselfResponse

interface FetchTourLocationMyselfUsecase {
    fun execute(): FetchTourLocationMyselfResponse
}