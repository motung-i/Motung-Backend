package kr.motung_i.backend.domain.review.presentation.dto

import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull

data class CreateReviewRequest(
    @field:NotEmpty
    val local: String,

    @field:NotNull
    val isRecommend: Boolean,

    @field:NotEmpty
    val description: String,
)