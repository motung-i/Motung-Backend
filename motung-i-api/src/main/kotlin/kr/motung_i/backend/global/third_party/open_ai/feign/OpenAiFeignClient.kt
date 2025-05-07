package kr.motung_i.backend.global.third_party.open_ai.feign

import kr.motung_i.backend.global.third_party.open_ai.configuration.OpenAiFeignConfiguration
import kr.motung_i.backend.global.third_party.open_ai.dto.request.CreateModelContentRequest
import kr.motung_i.backend.global.third_party.open_ai.dto.response.CreateModelContentResponse
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody

@FeignClient(
    name = "open-ai",
    url = "https://api.openai.com/v1/responses",
    configuration = [OpenAiFeignConfiguration::class]
)
interface OpenAiFeignClient {

    @PostMapping
    fun createModelContent(
        @RequestBody createModelContentRequest: CreateModelContentRequest,
    ): CreateModelContentResponse
}