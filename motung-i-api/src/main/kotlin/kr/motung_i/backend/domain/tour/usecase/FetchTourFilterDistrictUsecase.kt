package kr.motung_i.backend.domain.tour.usecase

import kr.motung_i.backend.domain.tour.formatter.TourFormatterService
import kr.motung_i.backend.domain.tour.presentation.dto.response.FetchTourFilterDistrictResponse
import kr.motung_i.backend.global.exception.CustomException
import kr.motung_i.backend.global.exception.enums.CustomErrorCode
import kr.motung_i.backend.global.geojson.LocalsCache
import kr.motung_i.backend.persistence.tour.entity.Country
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class FetchTourFilterDistrictUsecase(
    private val localsCache: LocalsCache,
    private val tourFormatterService: TourFormatterService,
) {
    fun execute(country: Country, region: String): FetchTourFilterDistrictResponse {
        val local = localsCache.findLocalByCountry(country)
            ?: throw RuntimeException(CustomException(CustomErrorCode.NOT_FOUND_COUNTRY_GEOJSON))

        val localByRegion =
            if (region == country.etc) {
                local.regions
                    .filter {
                        country.etc == tourFormatterService.formatToTourFilterRegion(it, country)
                    }
                    .map {
                        tourFormatterService.formatToTourFilterCityRegion(it, country)
                    }
            } else {
                local.regions
                    .filter { it.alias == region }
                    .flatMap { savedRegion ->
                        savedRegion.districts.map { district ->
                            tourFormatterService.formatToTourFilterDistrict(district, country)
                        }
                    }
            }

        val districts = localByRegion
            .groupBy(
                keySelector = { it.first },
                valueTransform = { it.second }
            )
            .mapValues { it.value.toSet() }

        return FetchTourFilterDistrictResponse.toDto(districts)
    }
}