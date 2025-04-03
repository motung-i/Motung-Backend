package kr.motung_i.backend.domain.item.presentation.dto.response.impl

import kr.motung_i.backend.domain.item.presentation.dto.response.ItemResponse
import kr.motung_i.backend.persistence.item.entity.Item

data class ApprovedItemResponse(
    override val name: String,
    override val description: String,
    val rankNumber: Int?,
) : ItemResponse {
    companion object {
        fun from(item: Item): ApprovedItemResponse =
            ApprovedItemResponse(
                item.name,
                item.description,
                item.rankNumber,
            )
    }
}