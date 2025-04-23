package kr.motung_i.backend.domain.tour.presentation.dto.response

data class FetchTourFilterDistrictResponse(
    val districts: List<DistrictsResponse>
) {
    companion object {
        fun toDto(districts: Map<String, Set<String>>): FetchTourFilterDistrictResponse =
            FetchTourFilterDistrictResponse(
                districts.map { DistrictsResponse.toDto(it) }
            )
    }
}