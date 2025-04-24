package kr.motung_i.backend.domain.tour.formatter

import kr.motung_i.backend.global.exception.CustomException
import kr.motung_i.backend.global.exception.enums.CustomErrorCode
import kr.motung_i.backend.global.geojson.dto.District
import kr.motung_i.backend.global.geojson.dto.Region
import kr.motung_i.backend.global.geojson.enums.Country
import org.springframework.stereotype.Component

@Component
class TourFormatterService(
    private val tourFormatterMap: Map<Country, TourFormatter>
) {
    fun formatToTourFilterRegion(region: Region, country: Country): String =
        tourFormatterMap[country]?.formatToTourFilterRegion(region, country)
            ?: throw CustomException(CustomErrorCode.NOT_FOUND_COUNTRY_FORMATTER)

    fun formatToTourFilterCityRegion(region: Region, country: Country): Pair<String, String> =
        tourFormatterMap[country]?.formatToTourFilterCityRegion(region, country)
            ?: throw CustomException(CustomErrorCode.NOT_FOUND_COUNTRY_FORMATTER)

    fun formatToTourFilterDistrict(district: District, country: Country): Pair<String, String> =
        tourFormatterMap[country]?.formatToTourFilterDistrict(district)
            ?: throw CustomException(CustomErrorCode.NOT_FOUND_COUNTRY_FORMATTER)
}