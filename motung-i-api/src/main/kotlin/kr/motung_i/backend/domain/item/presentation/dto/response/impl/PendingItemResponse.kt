package kr.motung_i.backend.domain.item.presentation.dto.response.impl

import kr.motung_i.backend.domain.item.presentation.dto.response.ItemResponse
import kr.motung_i.backend.persistence.item.entity.Item
import java.util.UUID

data class PendingItemResponse(
    val id: UUID?,
    override val name: String,
    override val description: String,
) : ItemResponse {
    companion object {
        fun from(item: Item): PendingItemResponse =
            PendingItemResponse(
                item.id,
                item.name,
                item.description,
            )
    }
}