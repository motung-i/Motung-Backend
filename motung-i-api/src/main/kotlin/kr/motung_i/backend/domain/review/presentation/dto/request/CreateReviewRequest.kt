package kr.motung_i.backend.domain.review.presentation.dto.request

import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull

data class CreateReviewRequest(
    @field:NotNull
    val isRecommend: Boolean,

    @field:NotEmpty
    val description: String,
)