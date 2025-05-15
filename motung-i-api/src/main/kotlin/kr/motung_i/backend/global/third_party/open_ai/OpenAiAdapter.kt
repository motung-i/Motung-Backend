package kr.motung_i.backend.global.third_party.open_ai

import kr.motung_i.backend.global.third_party.open_ai.dto.request.CreateModelContentRequest
import kr.motung_i.backend.global.third_party.open_ai.dto.response.CreateModelContentResponse
import kr.motung_i.backend.global.third_party.open_ai.feign.OpenAiFeignClient
import org.springframework.stereotype.Service

@Service
class OpenAiAdapter(
    private val openAiFeignClient: OpenAiFeignClient,
) {
    fun createModelContent(localAlias: String): CreateModelContentResponse {
        return openAiFeignClient.createModelContent(CreateModelContentRequest.fromLocalAlias(localAlias))
    }
}