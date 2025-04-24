package kr.motung_i.backend.global.geojson.formatter

interface LocalFormatter {
    fun formatToRegionAlias(region: String): String
    fun formatToDistrictAlias(district: String): String
    fun formatToLocalAlias(local: String): String
}