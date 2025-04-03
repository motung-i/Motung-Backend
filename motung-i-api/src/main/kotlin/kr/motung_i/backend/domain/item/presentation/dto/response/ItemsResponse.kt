package kr.motung_i.backend.domain.item.presentation.dto.response

import kr.motung_i.backend.persistence.item.entity.Item

data class ItemsResponse(
    val items: List<ItemResponse>,
) {
    companion object {
        fun from(items: List<Item>): ItemsResponse =
            ItemsResponse(items.map { ItemResponse.from(it) })
    }
}