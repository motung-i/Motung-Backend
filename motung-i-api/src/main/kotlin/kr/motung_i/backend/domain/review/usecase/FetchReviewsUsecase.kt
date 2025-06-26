package kr.motung_i.backend.domain.review.usecase

import kr.motung_i.backend.domain.review.presentation.dto.request.FetchReviewsRequest
import kr.motung_i.backend.domain.review.presentation.dto.response.FetchReviewsResponse

interface FetchReviewsUsecase {
    fun execute(request: FetchReviewsRequest): FetchReviewsResponse
}