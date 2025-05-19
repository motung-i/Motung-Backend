package kr.motung_i.backend.domain.review.presentation.dto.response

import kr.motung_i.backend.persistence.review.entity.Review
import java.time.LocalDate
import java.util.UUID

data class ReviewResponse(
    val reviewId: UUID?,
    val nickname: String?,
    val isRecommend: Boolean,
    val local: String,
    val description: String,
    val imageUrls: List<String>,
    val createdDate: LocalDate?
) {
    companion object {
        fun toDto(review: Review): ReviewResponse =
            ReviewResponse(
                reviewId = review.id,
                nickname = review.user.nickname,
                isRecommend = review.isRecommend,
                local = review.local.localAlias,
                description = review.description,
                imageUrls = review.imageUrls,
                createdDate = review.createdAt?.toLocalDate(),
            )
    }
}