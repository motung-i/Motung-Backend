package kr.motung_i.backend.global.formatter

interface TourFormatter {
    fun formatToTourFilterRegion(regionSet: Set<String>): Set<String>
}