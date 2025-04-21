package kr.motung_i.backend.global.geojson.dto.local

data class Neighborhood (
    val name: String,
) {
    companion object {
        fun toDto(neighborhood: String): Neighborhood =
            Neighborhood(
                name = neighborhood,
            )
    }
}