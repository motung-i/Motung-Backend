package kr.motung_i.backend.global.util

import kr.motung_i.backend.global.exception.CustomException
import kr.motung_i.backend.global.exception.enums.CustomErrorCode

abstract class ImageUtil {
    companion object {
        private val ALLOWED_IMAGE_EXTENSIONS = setOf("jpg", "jpeg", "png")

        fun getImageExtension(originalFilename: String): String {
            val extension = originalFilename.split(".").last()

            return extension.takeIf { it in ALLOWED_IMAGE_EXTENSIONS }
                ?: throw CustomException(CustomErrorCode.NOT_ALLOWED_IMAGE_EXTENSION)
        }
    }
}