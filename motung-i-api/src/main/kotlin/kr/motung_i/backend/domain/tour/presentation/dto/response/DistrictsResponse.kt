package kr.motung_i.backend.domain.tour.presentation.dto.response

data class DistrictsResponse(
    val type: String,
    val district: Set<String>,
) {
    companion object {
        fun toDto(districts: Map.Entry<String, Set<String>>): DistrictsResponse =
            DistrictsResponse(
                type = districts.key,
                district = districts.value,
            )
    }
}