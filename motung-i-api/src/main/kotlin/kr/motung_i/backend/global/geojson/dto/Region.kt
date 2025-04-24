    package kr.motung_i.backend.global.geojson.dto

    data class Region(
        val name: String,
        val alias: String,
        val districts: List<District>,
    )
