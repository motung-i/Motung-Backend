package kr.motung_i.backend.global.geojson.formatter

import kr.motung_i.backend.global.exception.CustomException
import kr.motung_i.backend.global.exception.enums.CustomErrorCode
import kr.motung_i.backend.global.geojson.enums.Country
import org.springframework.stereotype.Component

@Component
class LocalFormatterService(
    private val tourFormatterMap: Map<Country, LocalFormatter>
) {
    fun formatToRegionAlias(region: String, country: Country): String =
        tourFormatterMap[country]?.formatToRegionAlias(region)
            ?: throw CustomException(CustomErrorCode.NOT_FOUND_COUNTRY_FORMATTER)

    fun formatToDistrictAlias(district: String, country: Country): String =
        tourFormatterMap[country]?.formatToDistrictAlias(district)
            ?: throw CustomException(CustomErrorCode.NOT_FOUND_COUNTRY_FORMATTER)

    fun formatToLocalAlias(local: String, country: Country): String =
        tourFormatterMap[country]?.formatToLocalAlias(local)
            ?: throw CustomException(CustomErrorCode.NOT_FOUND_COUNTRY_FORMATTER)
}