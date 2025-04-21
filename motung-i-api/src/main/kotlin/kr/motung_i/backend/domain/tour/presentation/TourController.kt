package kr.motung_i.backend.domain.tour.presentation

import kr.motung_i.backend.domain.tour.presentation.dto.response.FetchTourFilterDistrictResponse
import kr.motung_i.backend.domain.tour.presentation.dto.response.FetchTourFilterRegionResponse
import kr.motung_i.backend.domain.tour.usecase.FetchTourFilterDistrictUsecase
import kr.motung_i.backend.domain.tour.usecase.FetchTourFilterRegionUsecase
import kr.motung_i.backend.global.geojson.enums.Country
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("tour")
class TourController(
    private val fetchTourFilterRegionUsecase: FetchTourFilterRegionUsecase,
    private val fetchTourFilterDistrictUsecase: FetchTourFilterDistrictUsecase,
) {
    @GetMapping("/filter/region")
    fun fetchTourFilterRegion(@RequestParam country: Country): ResponseEntity<FetchTourFilterRegionResponse> =
        fetchTourFilterRegionUsecase.execute(country).run {
            ResponseEntity.ok(this)
        }

    @GetMapping("/filter/district")
    fun fetchTourFilterDistrict(
        @RequestParam country: Country,
        @RequestParam region: String
    ): ResponseEntity<FetchTourFilterDistrictResponse> =
        fetchTourFilterDistrictUsecase.execute(country, region).run {
            ResponseEntity.ok(this)
        }
}