package kr.motung_i.backend.global.geojson.dto.local

data class Region(
    val name: String,
    val alias: String,
    val district: District,
)
