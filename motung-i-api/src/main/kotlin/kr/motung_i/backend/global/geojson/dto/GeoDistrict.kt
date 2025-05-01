package kr.motung_i.backend.global.geojson.dto

data class GeoDistrict(
    val name: String,
    val alias: String,
    val geoNeighborhoods: List<GeoNeighborhood>,
    val regionName: String,
)