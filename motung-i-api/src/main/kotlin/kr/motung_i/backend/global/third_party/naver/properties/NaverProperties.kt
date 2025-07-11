package kr.motung_i.backend.global.third_party.naver.properties

import jakarta.validation.constraints.NotBlank
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.validation.annotation.Validated

@Validated
@ConfigurationProperties("cloud.naver")
class NaverProperties(
    @field:NotBlank(message = "Naver ClientId 값은 필수 값 입니다.")
    val clientId: String,

    @field:NotBlank(message = "Naver ClientSecret 값은 필수 값 입니다.")
    val clientSecretKey: String,
)