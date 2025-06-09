package kr.motung_i.backend.domain.tour.presentation

import kr.motung_i.backend.domain.tour.presentation.dto.response.FetchRandomTourLocationResponse
import kr.motung_i.backend.domain.tour.presentation.dto.response.FetchTourFilterDistrictResponse
import kr.motung_i.backend.domain.tour.presentation.dto.response.FetchTourFilterRegionResponse
import kr.motung_i.backend.domain.tour.presentation.dto.response.FetchTourLocationMyselfResponse
import kr.motung_i.backend.domain.tour.usecase.CreateRandomTourLocationUsecase
import kr.motung_i.backend.domain.tour.usecase.CreateTourUsecase
import kr.motung_i.backend.domain.tour.usecase.FetchTourFilterDistrictUsecase
import kr.motung_i.backend.domain.tour.usecase.FetchTourFilterRegionUsecase
import kr.motung_i.backend.domain.tour.usecase.FetchTourLocationMyselfUsecase
import kr.motung_i.backend.persistence.tour_location.entity.Country
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("tour")
class TourController(
    private val fetchTourFilterRegionUsecase: FetchTourFilterRegionUsecase,
    private val fetchTourFilterDistrictUsecase: FetchTourFilterDistrictUsecase,
    private val createRandomTourLocationUsecase: CreateRandomTourLocationUsecase,
    private val createTourUsecase: CreateTourUsecase,
    private val fetchTourLocationMyselfUsecase: FetchTourLocationMyselfUsecase,
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

    @PostMapping("/random")
    fun createRandomTourLocation(
        @RequestParam country: Country,
        @RequestParam(defaultValue = "") region: List<String>,
        @RequestParam(defaultValue = "") district: List<String>,
    ): ResponseEntity<FetchRandomTourLocationResponse> =
        createRandomTourLocationUsecase.execute(country, region, district).run {
            ResponseEntity.ok(this)
        }

    @GetMapping
    fun fetchTourLocationMyself(): ResponseEntity<FetchTourLocationMyselfResponse> =
        fetchTourLocationMyselfUsecase.execute().run {
            ResponseEntity.ok(this)
        }

    @PostMapping
    fun createTour(): ResponseEntity<Unit> =
        createTourUsecase.execute().run {
            ResponseEntity.ok().build()
        }
}