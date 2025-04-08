package kr.motung_i.backend.domain.travel_info.presentation

import kr.motung_i.backend.domain.travel_info.presentation.dto.response.TravelInfoListResponse
import kr.motung_i.backend.domain.travel_info.usecase.FetchAllTravelInfoUsecase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/travel-info")
class TravelInfoController(
    private val fetchAllTravelInfoUsecase: FetchAllTravelInfoUsecase,
) {
    @GetMapping
    fun fetchAllTravelInfo(): ResponseEntity<TravelInfoListResponse> =
        fetchAllTravelInfoUsecase.execute().run {
            ResponseEntity.ok(this)
        }
}