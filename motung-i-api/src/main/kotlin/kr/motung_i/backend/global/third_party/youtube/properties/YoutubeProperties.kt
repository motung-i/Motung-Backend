package kr.motung_i.backend.global.third_party.youtube.properties

import jakarta.validation.constraints.NotBlank
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.validation.annotation.Validated

@Validated
@ConfigurationProperties("cloud.youtube")
class YoutubeProperties(
    @field:NotBlank(message = "유튜브 ApiKey 값은 필수 값 입니다.")
    val apiKey: String,
)