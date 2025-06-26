package kr.motung_i.backend.domain.review.usecase

import kr.motung_i.backend.domain.review.presentation.dto.request.RemoveReviewRequest
import java.util.UUID

interface RemoveReviewUsecase {
    fun execute(reviewId: UUID, request: RemoveReviewRequest)
}