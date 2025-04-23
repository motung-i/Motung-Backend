package kr.motung_i.backend.domain.tour.usecase

import kr.motung_i.backend.domain.tour.presentation.dto.response.FetchTourFilterDistrictResponse
import kr.motung_i.backend.global.exception.CustomException
import kr.motung_i.backend.global.exception.enums.CustomErrorCode
import kr.motung_i.backend.domain.tour.formatter.TourFormatterService
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
    fun execute(country: Country, region: String): FetchTourFilterDistrictResponse {
        val countryGeoJsonFeature = geoJsonFeaturesCache.findCountryGeoJsonFeatureByCountry(country)
            ?: throw RuntimeException(CustomException(CustomErrorCode.NOT_FOUND_COUNTRY_GEOJSON))

        val districts = countryGeoJsonFeature.geoJsonFeatures.filter {
            val formatRegion = tourFormatterService.formatToTourFilterRegion(
                region = it.local.region,
                country = country
            )
            formatRegion == region
        }.map {
            tourFormatterService.formatToTourFilterDistrict(
                district = it.local.region.district,
                country = country
            )
        }.groupBy(
            keySelector = { it.first },
            valueTransform = { it.second }
        ).mapValues {
            it.value.toSet()
        }


        return FetchTourFilterDistrictResponse.toDto(districts)
    }
}