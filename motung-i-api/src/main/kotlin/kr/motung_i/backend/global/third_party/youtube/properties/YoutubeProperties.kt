package kr.motung_i.backend.global.third_party.youtube.properties

import jakarta.validation.constraints.NotEmpty
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.validation.annotation.Validated

@Validated
@ConfigurationProperties("cloud.youtube")
class YoutubeProperties(
    @NotEmpty
    val apiKey: String,
)