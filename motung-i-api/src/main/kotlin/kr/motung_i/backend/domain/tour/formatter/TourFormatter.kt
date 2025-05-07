package kr.motung_i.backend.domain.tour.formatter

import kr.motung_i.backend.global.geojson.dto.GeoDistrict
import kr.motung_i.backend.global.geojson.dto.GeoRegion
import kr.motung_i.backend.persistence.tour_location.entity.Country

interface TourFormatter {
    fun formatToTourFilterRegion(geoRegion: GeoRegion, country: Country): String
    fun formatToTourFilterCityRegion(geoRegion: GeoRegion, country: Country): Pair<String, String>
    fun formatToTourFilterDistrict(geoDistrict: GeoDistrict): Pair<String, String>
}