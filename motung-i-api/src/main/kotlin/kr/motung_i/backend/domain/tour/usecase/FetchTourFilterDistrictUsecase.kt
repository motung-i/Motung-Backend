package kr.motung_i.backend.domain.tour.usecase

import kr.motung_i.backend.domain.tour.presentation.dto.response.FetchTourFilterDistrictResponse
import kr.motung_i.backend.global.exception.CustomException
import kr.motung_i.backend.global.exception.enums.CustomErrorCode
import kr.motung_i.backend.global.formatter.TourFormatterService
import kr.motung_i.backend.global.geojson.GeoJsonFeaturesCache
import kr.motung_i.backend.global.geojson.enums.Country
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class FetchTourFilterDistrictUsecase(
    private val geoJsonFeaturesCache: GeoJsonFeaturesCache,
    private val tourFormatterService: TourFormatterService,
) {
    fun execute(country: Country, requestRegion: String): FetchTourFilterDistrictResponse {
        val countryGeoJsonFeature = geoJsonFeaturesCache.findCountryGeoJsonFeatureByCountry(country)
            ?: throw RuntimeException(CustomException(CustomErrorCode.NOT_FOUND_COUNTRY_GEOJSON))

        val districts = countryGeoJsonFeature.geoJsonFeatures
            .filter {
                val savedRegion = it.local.region.name
                tourFormatterService.formatToTourFilterRegion(savedRegion, country) == requestRegion
            }
            .map {
                val district = it.local.region.district.name
                tourFormatterService.formatToTourFilterDistrict(district, country)
            }
            .groupBy(
                keySelector = { it.first },
                valueTransform = { it.second }
            ).mapValues { it.value.toSet() }


        return FetchTourFilterDistrictResponse.toDto(districts)
    }
}