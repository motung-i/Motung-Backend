package kr.motung_i.backend.domain.review.usecase

import kr.motung_i.backend.domain.review.presentation.dto.request.ReportReviewRequest
import java.util.UUID

interface ReportReviewUsecase {
    fun execute(reviewId: UUID, request: ReportReviewRequest)
}