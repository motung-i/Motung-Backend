package kr.motung_i.backend.domain.tour.usecase.impl

import kr.motung_i.backend.domain.tour.formatter.TourFormatterService
import kr.motung_i.backend.domain.tour.presentation.dto.response.FetchTourFilterRegionResponse
import kr.motung_i.backend.domain.tour.usecase.FetchTourFilterRegionUsecase
import kr.motung_i.backend.global.exception.CustomException
import kr.motung_i.backend.global.exception.enums.CustomErrorCode
import kr.motung_i.backend.global.geojson.LocalsCache
import kr.motung_i.backend.persistence.tour.entity.Country
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.text.Collator
import java.util.*

@Service
@Transactional(readOnly = true)
class FetchTourFilterRegionUsecaseImpl(
    private val localsCache: LocalsCache,
    private val tourFormatterService: TourFormatterService
) : FetchTourFilterRegionUsecase {
    override fun execute(country: Country): FetchTourFilterRegionResponse {
        val local = localsCache.findLocalByCountry(country)
            ?: throw CustomException(CustomErrorCode.NOT_FOUND_COUNTRY_GEOJSON)

        val regions = linkedSetOf(country.etc).apply {
            addAll(
                local.geoRegions
                    .map { tourFormatterService.formatToTourFilterRegion(it, country) }
                    .filter { it != country.etc }
                    .toSortedSet(Collator.getInstance(Locale.KOREA))
            )
        }

        return FetchTourFilterRegionResponse(regions)
    }
}