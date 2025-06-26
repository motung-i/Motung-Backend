package kr.motung_i.backend.domain.travel_info.usecase

import kr.motung_i.backend.domain.travel_info.presentation.dto.request.UpdateTravelInfoRequest
import org.springframework.web.multipart.MultipartFile
import java.util.UUID

interface UpdateTravelInfoUsecase {
    fun execute(travelId: UUID, image: MultipartFile?, request: UpdateTravelInfoRequest)
}