package kr.motung_i.backend.domain.review.presentation.dto.request

import jakarta.validation.constraints.Size
import kr.motung_i.backend.persistence.review_report.entity.ReportReason

data class ReportReviewRequest(
    @field:Size(min = 1)
    val reasons: Set<ReportReason>,
)