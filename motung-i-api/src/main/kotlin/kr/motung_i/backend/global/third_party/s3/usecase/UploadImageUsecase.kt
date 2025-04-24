package kr.motung_i.backend.global.third_party.s3.usecase

import kr.motung_i.backend.global.third_party.s3.properties.S3Properties
import kr.motung_i.backend.global.util.ImageUtil.Companion.getImageExtension
import kr.motung_i.backend.global.util.ImageUtil.Companion.getImageContentType
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import software.amazon.awssdk.core.sync.RequestBody
import software.amazon.awssdk.services.s3.S3Client
import software.amazon.awssdk.services.s3.model.GetUrlRequest
import software.amazon.awssdk.services.s3.model.ObjectCannedACL
import software.amazon.awssdk.services.s3.model.PutObjectRequest
import java.time.LocalDateTime
import java.util.UUID.randomUUID

@Service
class UploadImageUsecase(
    private val s3Client: S3Client,
    private val s3Properties: S3Properties,
) {
    fun execute(image: MultipartFile?): String? =
        image?.let {
            if (image.isEmpty) {
                return null
            }

            val originalFilename = requireNotNull(it.originalFilename)
            val extension = getImageExtension(originalFilename)
            val fileName = "${LocalDateTime.now()}:${randomUUID()}.$extension"

            val putObjectRequest =
                PutObjectRequest.builder()
                    .bucket(s3Properties.bucketName)
                    .key(fileName)
                    .contentType(getImageContentType(originalFilename))
                    .acl(ObjectCannedACL.PUBLIC_READ)
                    .build()
            val requestBody = RequestBody.fromInputStream(it.inputStream, it.size)

            s3Client.putObject(putObjectRequest, requestBody)

            val request = GetUrlRequest.builder().bucket(s3Properties.bucketName).key(fileName).build()
            "${s3Client.utilities().getUrl(request)}"
        }
}