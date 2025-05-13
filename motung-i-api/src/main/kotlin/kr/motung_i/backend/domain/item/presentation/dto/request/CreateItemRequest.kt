package kr.motung_i.backend.domain.item.presentation.dto.request

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern

data class CreateItemRequest(
    @field:NotBlank
    val itemName: String,

    @field:Pattern(
        regexp = """^https?://(?:www|m)\.coupang\.com/vp/products/(\d+)(?:\?[^\s#]*)?$""",
        message = "유효한 쿠팡 링크를 입력해 주세요."
    )
    val coupangUrl: String,

    @field:NotBlank
    val description: String,
)