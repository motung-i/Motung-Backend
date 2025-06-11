package kr.motung_i.backend.domain.tour.formatter

import kr.motung_i.backend.global.exception.CustomException
import kr.motung_i.backend.global.exception.enums.CustomErrorCode
import kr.motung_i.backend.global.geojson.dto.GeoDistrict
import kr.motung_i.backend.global.geojson.dto.GeoRegion
import kr.motung_i.backend.persistence.tour.entity.Country
import org.springframework.stereotype.Component

@Component
class TourFormatterService(
    private val tourFormatterMap: Map<Country, TourFormatter>
) {
    fun formatToTourFilterRegion(geoRegion: GeoRegion, country: Country): String =
        tourFormatterMap[country]?.formatToTourFilterRegion(geoRegion, country)
            ?: throw CustomException(CustomErrorCode.NOT_FOUND_COUNTRY_FORMATTER)

    fun formatToTourFilterCityRegion(geoRegion: GeoRegion, country: Country): Pair<String, String> =
        tourFormatterMap[country]?.formatToTourFilterCityRegion(geoRegion, country)
            ?: throw CustomException(CustomErrorCode.NOT_FOUND_COUNTRY_FORMATTER)

    fun formatToTourFilterDistrict(geoDistrict: GeoDistrict, country: Country): Pair<String, String> =
        tourFormatterMap[country]?.formatToTourFilterDistrict(geoDistrict)
            ?: throw CustomException(CustomErrorCode.NOT_FOUND_COUNTRY_FORMATTER)
}