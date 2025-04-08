package kr.motung_i.backend.global.third_party.s3.usecase

import kr.motung_i.backend.global.exception.CustomException
import kr.motung_i.backend.global.exception.enums.CustomErrorCode
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

@Service
class UpdateImageUsecase(
    private val uploadImageUsecase: UploadImageUsecase,
    private val deleteImageUsecase: DeleteImageUsecase,
) {
    fun execute(savedImageUrl: String, newImage: MultipartFile?): String? {
        if (newImage == null || newImage.isEmpty) {
            return null
        }

        val newImageUrl = uploadImageUsecase.execute(newImage) ?: throw CustomException(CustomErrorCode.NOT_FOUND_IMAGE)
        deleteImageUsecase.execute(savedImageUrl)

        return newImageUrl
    }
}