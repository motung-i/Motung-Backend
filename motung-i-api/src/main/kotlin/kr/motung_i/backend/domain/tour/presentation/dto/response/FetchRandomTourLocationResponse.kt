package kr.motung_i.backend.domain.tour.presentation.dto.response

data class FetchRandomTourLocationResponse(
    val lat: Double,
    val lon: Double,
    val local: String,
    val geometry: GeometryResponse,
)