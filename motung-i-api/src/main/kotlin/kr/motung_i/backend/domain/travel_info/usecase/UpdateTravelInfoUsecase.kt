package kr.motung_i.backend.domain.travel_info.usecase

import kr.motung_i.backend.domain.travel_info.presentation.dto.request.UpdateTravelInfoRequest
import kr.motung_i.backend.global.exception.CustomException
import kr.motung_i.backend.global.exception.enums.CustomErrorCode
import kr.motung_i.backend.global.third_party.s3.usecase.UpdateImageUsecase
import kr.motung_i.backend.persistence.travel_info.repository.TravelInfoRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile
import java.util.UUID

@Service
@Transactional
class UpdateTravelInfoUsecase(
    private val travelInfoRepository: TravelInfoRepository,
    private val updateImageUsecase: UpdateImageUsecase,
) {
    fun execute(travelId: UUID, image: MultipartFile?, request: UpdateTravelInfoRequest) {
        val savedTravelInfo = travelInfoRepository.findById(travelId) ?: throw CustomException(CustomErrorCode.NOT_FOUND_TRAVEL_INFO)
        val newImageUrl = updateImageUsecase.execute(savedTravelInfo.imageUrl, image)

        travelInfoRepository.save(
            savedTravelInfo.update(
                newImageUrl,
                request.title,
                request.description
            )
        )
    }
}