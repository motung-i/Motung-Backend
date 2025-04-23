package kr.motung_i.backend.domain.tour.formatter

import kr.motung_i.backend.global.geojson.dto.local.District
import kr.motung_i.backend.global.geojson.dto.local.Region

interface TourFormatter {
    fun formatToTourFilterRegion(region: Region): String
    fun formatToTourFilterDistrict(district: District): Pair<Char, String>
}