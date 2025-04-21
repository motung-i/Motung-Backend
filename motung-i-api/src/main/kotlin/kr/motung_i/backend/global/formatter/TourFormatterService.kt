package kr.motung_i.backend.global.formatter

import kr.motung_i.backend.global.exception.CustomException
import kr.motung_i.backend.global.exception.enums.CustomErrorCode
import kr.motung_i.backend.global.geojson.enums.Country
import org.springframework.stereotype.Component

@Component
class TourFormatterService(
    private val tourFormatterMap: Map<Country, TourFormatter>
) {
    fun formatToTourFilterRegion(region: String, country: Country): String =
        tourFormatterMap[country]?.formatToTourFilterRegion(region)
            ?: throw CustomException(CustomErrorCode.NOT_FOUND_COUNTRY_FORMATTER)

    fun formatToTourFilterDistrict(district: String, country: Country): Pair<Char, String> =
        tourFormatterMap[country]?.formatToTourFilterDistricts(district)
            ?: throw CustomException(CustomErrorCode.NOT_FOUND_COUNTRY_FORMATTER)
}