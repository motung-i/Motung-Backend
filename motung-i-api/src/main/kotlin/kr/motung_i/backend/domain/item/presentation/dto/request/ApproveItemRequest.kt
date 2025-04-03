package kr.motung_i.backend.domain.item.presentation.dto.request

import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min

data class ApproveItemRequest(
    @field:Min(1)
    @field:Max(5)
    val rankNumber: Int
)
