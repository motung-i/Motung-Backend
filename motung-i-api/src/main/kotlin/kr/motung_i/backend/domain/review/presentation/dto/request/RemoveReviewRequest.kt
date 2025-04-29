package kr.motung_i.backend.domain.review.presentation.dto.request

import kr.motung_i.backend.persistence.user_suspension.entity.SuspensionPeriod

data class RemoveReviewRequest(
    val suspensionPeriod: SuspensionPeriod?
)