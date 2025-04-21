package kr.motung_i.backend.global.geojson.dto.local

data class District(
    val name: String,
    val neighborhood: Neighborhood,
) {
    companion object {
        fun toDto(district: String, neighborhood: String): District =
            District(
                name = district,
                neighborhood = Neighborhood.toDto(neighborhood),
            )
    }
}