package kr.motung_i.backend.domain.item.presentation.dto.request

import jakarta.validation.constraints.NotBlank

data class CreateItemRequest(
    @field:NotBlank
    val itemName: String,

    @field:NotBlank
    val description: String,
)