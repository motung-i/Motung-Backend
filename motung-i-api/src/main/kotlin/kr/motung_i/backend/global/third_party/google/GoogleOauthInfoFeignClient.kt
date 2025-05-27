package kr.motung_i.backend.global.third_party.google

import kr.motung_i.backend.global.third_party.google.dto.GoogleInfoResDto
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestHeader

@FeignClient(name = "GoogleOauthInfoClient", url = "https://www.googleapis.com")
interface GoogleOauthInfoFeignClient {
    @GetMapping("/oauth2/v1/userinfo")
    fun getInfo(
        @RequestHeader("Authorization") accessToken: String,
    ): GoogleInfoResDto
}
