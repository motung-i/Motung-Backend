package kr.motung_i.backend.domain.travel_info.usecase

import kr.motung_i.backend.domain.travel_info.presentation.dto.request.CreateTravelInfoRequest
import kr.motung_i.backend.global.exception.CustomException
import kr.motung_i.backend.global.exception.enums.CustomErrorCode
import kr.motung_i.backend.global.third_party.s3.usecase.UploadImageUsecase
import kr.motung_i.backend.persistence.travel_info.entity.TravelInfo
import kr.motung_i.backend.persistence.travel_info.repository.TravelInfoRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile

@Service
@Transactional
class CreateTravelInfoUsecase(
    private val uploadImageUsecase: UploadImageUsecase,
    private val travelInfoRepository: TravelInfoRepository,
) {
    fun execute(image: MultipartFile, createTravelInfoRequest: CreateTravelInfoRequest) {
        val imageUrl = uploadImageUsecase.execute(image) ?: throw CustomException(CustomErrorCode.NOT_FOUND_IMAGE)

        travelInfoRepository.save(
            TravelInfo(
                imageUrl = imageUrl,
                title = createTravelInfoRequest.title,
                description = createTravelInfoRequest.description,
            )
        )
    }
}