package kr.motung_i.backend.domain.review.presentation.dto.response

import kr.motung_i.backend.persistence.review.entity.Review
import kr.motung_i.backend.persistence.review_report.entity.ReportReason
import java.time.LocalDate
import java.util.UUID

data class DetailReviewResponse(
    val reviewId: UUID?,
    val nickname: String?,
    val isRecommend: Boolean,
    val local: String,
    val description: String,
    val imageUrls: List<String>,
    val createdDate: LocalDate?,
    val isReported: Boolean,
    val reasons: Set<String>,
) {
    companion object {
        fun toDto(review: Review): DetailReviewResponse =
            DetailReviewResponse(
                reviewId = review.id,
                nickname = review.user.nickname,
                isRecommend = review.isRecommend,
                local = review.local.localAlias,
                description = review.description,
                imageUrls = review.imageUrls,
                createdDate = review.createdAt?.toLocalDate(),
                isReported = review.reports.isNotEmpty(),
                reasons = review.reports
                    .flatMap { it.reasons.map(ReportReason::description) }
                    .toSet()
            )
    }
}