package kr.motung_i.backend.global.formatter

interface TourFormatter {
    fun formatToTourFilterRegion(region: String): String
    fun formatToTourFilterDistricts(district: String): Pair<Char, String>
}