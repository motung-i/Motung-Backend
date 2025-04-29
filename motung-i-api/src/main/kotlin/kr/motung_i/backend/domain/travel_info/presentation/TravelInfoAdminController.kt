package kr.motung_i.backend.domain.travel_info.presentation

import kr.motung_i.backend.domain.travel_info.presentation.dto.request.UpdateTravelInfoRequest
import kr.motung_i.backend.domain.travel_info.usecase.UpdateTravelInfoUsecase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import java.util.UUID

@RestController
@RequestMapping("/admin/travel-info")
class TravelInfoAdminController(
    private val updateTravelInfoUsecase: UpdateTravelInfoUsecase,
) {
    @PatchMapping("{travelInfoId}")
    fun updateTravelInfo(
        @PathVariable travelInfoId: UUID,
        @RequestPart("image") image: MultipartFile,
        @RequestPart("request") request: UpdateTravelInfoRequest,
    ): ResponseEntity<Unit> =
        updateTravelInfoUsecase.execute(travelInfoId, image, request).run {
            ResponseEntity.noContent().build()
        }
}
