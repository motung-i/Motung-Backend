package kr.motung_i.backend.domain.item.presentation.dto.request

import kr.motung_i.backend.persistence.user_suspension.entity.SuspensionPeriod

data class RemoveItemRequest(
    val suspensionPeriod: SuspensionPeriod,
)