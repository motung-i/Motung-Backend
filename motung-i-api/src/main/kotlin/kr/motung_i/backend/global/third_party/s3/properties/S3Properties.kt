package kr.motung_i.backend.global.third_party.s3.properties

import jakarta.validation.constraints.NotEmpty
import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties("cloud.aws.s3")
class S3Properties(
    @NotEmpty
    val accessKey: String,

    @NotEmpty
    val secretKey: String,

    @NotEmpty
    val region: String,

    @NotEmpty
    val bucketName: String,
)