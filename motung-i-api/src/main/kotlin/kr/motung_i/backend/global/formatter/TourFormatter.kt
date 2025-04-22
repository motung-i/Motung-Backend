package kr.motung_i.backend.global.formatter

interface TourFormatter {
    fun formatToTourFilterRegion(region: String): String
    fun formatToTourRegion(region: String): String
    fun formatToTourFilterDistrict(district: String): Pair<Char, String>
    fun formatToTourDistrict(district: String): String
}