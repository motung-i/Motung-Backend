package kr.motung_i.backend.domain.tour.usecase.impl

import kr.motung_i.backend.domain.tour.formatter.TourFormatterService
import kr.motung_i.backend.domain.tour.presentation.dto.response.FetchTourFilterDistrictResponse
import kr.motung_i.backend.domain.tour.usecase.FetchTourFilterDistrictUsecase
import kr.motung_i.backend.global.exception.CustomException
import kr.motung_i.backend.global.exception.enums.CustomErrorCode
import kr.motung_i.backend.global.geojson.LocalsCache
import kr.motung_i.backend.persistence.tour.entity.Country
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.text.Collator
import java.util.Locale

@Service
@Transactional
class FetchTourFilterDistrictUsecaseImpl(
    private val localsCache: LocalsCache,
    private val tourFormatterService: TourFormatterService,
): FetchTourFilterDistrictUsecase {
    override fun execute(country: Country, region: String): FetchTourFilterDistrictResponse {
        val local = localsCache.findLocalByCountry(country)
            ?: throw CustomException(CustomErrorCode.NOT_FOUND_COUNTRY_GEOJSON)

        val localByRegion =
            if (region == country.etc) {
                local.geoRegions
                    .filter { country.etc == tourFormatterService.formatToTourFilterRegion(it, country) }
                    .map { tourFormatterService.formatToTourFilterCityRegion(it, country) }
            } else {
                local.geoRegions
                    .filter { it.alias == region }
                    .flatMap { savedRegion ->
                        savedRegion.geoDistricts.map { district ->
                            tourFormatterService.formatToTourFilterDistrict(district, country)
                        }
                    }
            }

        val districts = localByRegion
            .groupBy(
                keySelector = { it.first },
                valueTransform = { it.second }
            ).mapValues { (_, value) ->
                value.toSortedSet(Collator.getInstance(Locale.KOREA))
            }

        return FetchTourFilterDistrictResponse.toDto(districts)
    }
}