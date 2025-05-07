package kr.motung_i.backend.global.third_party.open_ai.properties

import jakarta.validation.constraints.NotEmpty
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.validation.annotation.Validated

@Validated
@ConfigurationProperties("cloud.open-ai")
class OpenAIProperties(
    @NotEmpty
    val apiKey: String,
)