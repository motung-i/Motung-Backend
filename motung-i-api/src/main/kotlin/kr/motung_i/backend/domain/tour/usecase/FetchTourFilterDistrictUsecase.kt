package kr.motung_i.backend.domain.tour.usecase

import kr.motung_i.backend.domain.tour.formatter.TourFormatterService
import kr.motung_i.backend.domain.tour.presentation.dto.response.FetchTourFilterDistrictResponse
import kr.motung_i.backend.global.exception.CustomException
import kr.motung_i.backend.global.exception.enums.CustomErrorCode
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

        val geoJsonFeaturesByRegion =
            if (region == country.etc) {
                countryGeoJsonFeature.geoJsonFeatures.filter {
                    it.local.region.alias != tourFormatterService.formatToTourFilterRegion(it.local.region, country)
                }.map {
                    tourFormatterService.formatToTourFilterCityRegion(it.local.region, country)
                }
            } else {
                countryGeoJsonFeature.geoJsonFeatures.filter {
                    it.local.region.alias == region
                }.map {
                    tourFormatterService.formatToTourFilterDistrict(
                        district = it.local.region.district,
                        country = country
                    )
                }
            }

        val districts = geoJsonFeaturesByRegion.groupBy(
            keySelector = { it.first },
            valueTransform = { it.second }
        ).mapValues {
            it.value.toSet()
        }


        return FetchTourFilterDistrictResponse.toDto(districts)
    }
}