package kr.motung_i.backend.domain.review.presentation.dto.request

import kr.motung_i.backend.global.geojson.enums.Country

data class FetchReviewsRequest(
    val country: Country?,
    val region: String?,
    val district: String?,
    val neighborhood: String?,
    val local: String?,
    val onlyByImage: Boolean?,
)