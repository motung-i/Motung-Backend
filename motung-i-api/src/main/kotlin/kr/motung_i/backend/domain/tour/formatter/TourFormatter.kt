package kr.motung_i.backend.domain.tour.formatter

import kr.motung_i.backend.global.geojson.dto.District
import kr.motung_i.backend.global.geojson.dto.Region
import kr.motung_i.backend.global.geojson.enums.Country

interface TourFormatter {
    fun formatToTourFilterRegion(region: Region, country: Country): String
    fun formatToTourFilterCityRegion(region: Region, country: Country): Pair<String, String>
    fun formatToTourFilterDistrict(district: District): Pair<String, String>
}