package kr.motung_i.backend.global.geojson.dto.local

data class Local(
    val name: String,
    val region: Region,
) {
    companion object {
        fun toDto(local: String, region: String, district: String, neighborhood: String): Local =
            Local(
                name = local,
                region = Region.toDto(region, district, neighborhood)
            )
    }
}