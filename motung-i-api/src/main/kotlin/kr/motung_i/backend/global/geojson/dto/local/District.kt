package kr.motung_i.backend.global.geojson.dto.local

data class District(
    val name: String,
    val alias: String,
    val neighborhood: Neighborhood,
)