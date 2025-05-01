    package kr.motung_i.backend.global.geojson.dto

    data class GeoRegion(
        val name: String,
        val alias: String,
        val geoDistricts: List<GeoDistrict>,
    )
