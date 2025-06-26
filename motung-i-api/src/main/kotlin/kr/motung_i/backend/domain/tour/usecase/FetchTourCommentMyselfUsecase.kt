package kr.motung_i.backend.domain.tour.usecase

import kr.motung_i.backend.domain.tour.presentation.dto.response.FetchTourCommentMyselfResponse

interface FetchTourCommentMyselfUsecase {
    fun execute(): FetchTourCommentMyselfResponse
}