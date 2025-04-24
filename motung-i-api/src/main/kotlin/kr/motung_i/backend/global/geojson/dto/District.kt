package kr.motung_i.backend.global.geojson.dto

data class District(
    val name: String,
    val alias: String,
    val neighborhoods: List<Neighborhood>,
    val regionName: String,
)