package kr.motung_i.backend.global.geojson.dto.local

data class Region(
    val name: String,
    val district: District,
) {
    companion object {
        fun toDto(region: String, district: String, neighborhood: String): Region =
            Region(
                name = region,
                district = District.toDto(district, neighborhood),
            )
    }
}
