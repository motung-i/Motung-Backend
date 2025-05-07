package kr.motung_i.backend.domain.review.presentation.dto.request

import kr.motung_i.backend.persistence.tour_location.entity.Country

data class FetchDetailReviewsRequest(
    val country: Country?,
    val region: String?,
    val district: String?,
    val neighborhood: String?,
    val local: String?,
    val onlyByImage: Boolean?,
    val onlyByReport: Boolean?,
)