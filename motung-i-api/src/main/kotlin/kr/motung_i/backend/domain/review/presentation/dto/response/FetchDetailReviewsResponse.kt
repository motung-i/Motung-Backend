package kr.motung_i.backend.domain.review.presentation.dto.response

import kr.motung_i.backend.persistence.review.entity.Review

data class FetchDetailReviewsResponse(
    val reviews: List<DetailReviewResponse>,
    val reviewsCount: Int,
) {
    companion object {
        fun toDto(reviews: List<Review>):FetchDetailReviewsResponse =
            FetchDetailReviewsResponse(
                reviews = reviews.map { DetailReviewResponse.toDto(it) },
                reviewsCount = reviews.size,
            )
    }
}