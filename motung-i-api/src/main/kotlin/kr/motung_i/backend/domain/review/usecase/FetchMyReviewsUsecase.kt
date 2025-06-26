package kr.motung_i.backend.domain.review.usecase

import kr.motung_i.backend.domain.review.presentation.dto.response.FetchMyReviewsResponse

interface FetchMyReviewsUsecase {
    fun execute(): FetchMyReviewsResponse
}