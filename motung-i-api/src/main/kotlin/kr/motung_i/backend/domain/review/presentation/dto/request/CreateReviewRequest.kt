package kr.motung_i.backend.domain.review.presentation.dto.request

import jakarta.validation.constraints.Size
import jakarta.validation.constraints.NotNull

data class CreateReviewRequest(
    @field:NotNull
    val isRecommend: Boolean,

    @field:Size(min = 1, max = 250)
    val description: String,
)