package kr.motung_i.backend.global.geojson.dto

import kr.motung_i.backend.global.geojson.enums.Country

data class GeoJsonFeatures(
    val country: Country,
    val geoJsonFeatures: List<GeoJsonFeature>,
)