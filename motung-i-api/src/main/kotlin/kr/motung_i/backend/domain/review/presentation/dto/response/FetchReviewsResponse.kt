package kr.motung_i.backend.domain.review.presentation.dto.response

import kr.motung_i.backend.persistence.review.entity.Review

data class FetchReviewsResponse(
    val reviews: List<ReviewResponse>,
    val reviewsCount: Int,
) {
    companion object {
        fun toDto(reviews: List<Review>):FetchReviewsResponse =
            FetchReviewsResponse(
                reviews = reviews.map { ReviewResponse.toDto(it) },
                reviewsCount = reviews.size,
            )
    }
}