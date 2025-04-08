package kr.motung_i.backend.global.third_party.s3.usecase

import kr.motung_i.backend.global.third_party.s3.properties.S3Properties
import kr.motung_i.backend.global.util.ImageUtil.Companion.getImageNameFromUrl
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import software.amazon.awssdk.services.s3.S3Client

@Service
class DeleteImageUsecase(
    private val s3Client: S3Client,
    private val s3Properties: S3Properties,
) {
    private val logger: Logger = LoggerFactory.getLogger(this::class.java)

    fun execute(imageUrl: String) {
        try {
            s3Client.deleteObject { builder -> builder
                .bucket(s3Properties.bucketName)
                .key(getImageNameFromUrl(imageUrl))
            }
        } catch (e: Exception) {
            logger.warn("{} 이미지가 삭제되지 않았습니다. | error: {}", imageUrl, e.message)
        }
    }
}