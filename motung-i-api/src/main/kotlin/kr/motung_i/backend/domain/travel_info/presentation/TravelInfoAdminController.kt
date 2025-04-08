package kr.motung_i.backend.domain.travel_info.presentation

import kr.motung_i.backend.domain.travel_info.presentation.dto.request.CreateTravelInfoRequest
import kr.motung_i.backend.domain.travel_info.usecase.CreateTravelInfoUsecase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/admin/travel-info")
class TravelInfoAdminController(
    private val createTravelInfoUsecase: CreateTravelInfoUsecase,
) {
    @PostMapping
    fun createTravelInfo(
        @RequestPart("image") image: MultipartFile,
        @RequestPart("request") createTravelInfoRequest: CreateTravelInfoRequest,
    ): ResponseEntity<Unit> =
        createTravelInfoUsecase.execute(image, createTravelInfoRequest).run {
            ResponseEntity.noContent().build()
        }
}
