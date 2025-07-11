package kr.motung_i.backend.global.third_party.open_ai.properties

import jakarta.validation.constraints.NotBlank
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.validation.annotation.Validated

@Validated
@ConfigurationProperties("cloud.open-ai")
class OpenAIProperties(
    @field:NotBlank(message = "OpenAi ApiKey 값은 필수 값 입니다.")
    val apiKey: String,
)