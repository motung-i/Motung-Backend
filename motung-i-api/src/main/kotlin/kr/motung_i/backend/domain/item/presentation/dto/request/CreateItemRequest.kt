package kr.motung_i.backend.domain.item.presentation.dto.request

data class CreateItemRequest(
    val itemName: String,
    val description: String,
)