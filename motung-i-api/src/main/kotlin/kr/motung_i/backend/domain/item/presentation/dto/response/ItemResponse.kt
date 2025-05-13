package kr.motung_i.backend.domain.item.presentation.dto.response

import kr.motung_i.backend.domain.item.presentation.dto.response.impl.ApprovedItemResponse
import kr.motung_i.backend.domain.item.presentation.dto.response.impl.PendingItemResponse
import kr.motung_i.backend.persistence.item.entity.Item
import kr.motung_i.backend.persistence.item.entity.enums.ItemStatus

interface ItemResponse {
    val name: String
    val coupangUrl: String
    val description: String

    companion object {
        fun from(item: Item): ItemResponse =
            when (item.itemStatus) {
                ItemStatus.APPROVED -> ApprovedItemResponse.from(item)
                ItemStatus.PENDING -> PendingItemResponse.from(item)
            }
    }
}