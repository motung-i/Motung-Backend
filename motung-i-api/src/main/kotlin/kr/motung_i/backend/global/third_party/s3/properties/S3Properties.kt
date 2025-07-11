package kr.motung_i.backend.global.third_party.s3.properties

import jakarta.validation.constraints.NotBlank
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.validation.annotation.Validated

@Validated
@ConfigurationProperties("cloud.aws.s3")
class S3Properties(
    @field:NotBlank(message = "S3 AccessKey 값은 필수 값 입니다.")
    val accessKey: String,

    @field:NotBlank(message = "S3 SecretKey 값은 필수 값 입니다.")
    val secretKey: String,

    @field:NotBlank(message = "S3 Region 값은 필수 값 입니다.")
    val region: String,

    @field:NotBlank(message = "S3 BucketName 값은 필수 값 입니다.")
    val bucketName: String,
)