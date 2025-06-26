package kr.motung_i.backend.domain.review.usecase

import kr.motung_i.backend.domain.review.presentation.dto.request.FetchDetailReviewsRequest
import kr.motung_i.backend.domain.review.presentation.dto.response.FetchDetailReviewsResponse

interface FetchDetailReviewsUsecase {
    fun execute(request: FetchDetailReviewsRequest): FetchDetailReviewsResponse
}