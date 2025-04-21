package kr.motung_i.backend.domain.tour.presentation.dto.response

data class DistrictsResponse(
    val type: Char,
    val district: Set<String>,
) {
    companion object {
        fun toDto(districts: Map.Entry<Char, Set<String>>): DistrictsResponse =
            DistrictsResponse(
                type = districts.key,
                district = districts.value,
            )
    }
}