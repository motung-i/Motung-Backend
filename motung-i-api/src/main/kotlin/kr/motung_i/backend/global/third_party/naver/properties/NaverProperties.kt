package kr.motung_i.backend.global.third_party.naver.properties

import jakarta.validation.constraints.NotEmpty
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.validation.annotation.Validated

@Validated
@ConfigurationProperties("cloud.naver")
class NaverProperties(
    @NotEmpty
    val clientId: String,

    @NotEmpty
    val clientSecretKey: String,
)