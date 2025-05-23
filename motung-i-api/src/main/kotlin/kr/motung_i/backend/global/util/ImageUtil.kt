package kr.motung_i.backend.global.util

import kr.motung_i.backend.global.exception.CustomException
import kr.motung_i.backend.global.exception.enums.CustomErrorCode
import java.net.URI
import java.net.URLDecoder
import java.nio.charset.StandardCharsets

abstract class ImageUtil {
    companion object {
        private val ALLOWED_IMAGE_EXTENSIONS = mapOf(
            "jpg" to "jpeg",
            "jpeg" to "jpeg",
            "png" to "png"
        )

        fun getImageExtension(filename: String): String =
            getExtension(filename).takeIf { it in ALLOWED_IMAGE_EXTENSIONS }
                ?: throw CustomException(CustomErrorCode.NOT_ALLOWED_IMAGE_EXTENSION)

        fun getImageContentType(filename: String): String =
            ALLOWED_IMAGE_EXTENSIONS[getExtension(filename)]
                ?: throw CustomException(CustomErrorCode.NOT_ALLOWED_IMAGE_EXTENSION)

        private fun getExtension(filename: String): String =
            filename.split(".").last()

        fun getImageNameFromUrl(uri: String): String {
            val rawPath = URI.create(uri).path
            return URLDecoder.decode(rawPath.substring(1), StandardCharsets.UTF_8)
        }
    }
}