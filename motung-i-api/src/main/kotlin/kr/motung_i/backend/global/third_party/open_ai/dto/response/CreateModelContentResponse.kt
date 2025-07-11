package kr.motung_i.backend.global.third_party.open_ai.dto.response

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import kr.motung_i.backend.global.third_party.open_ai.dto.OpenAiRecommendation

data class CreateModelContentResponse(
    val output: List<Output>
) {
    data class Output(
        val content: List<Content>,
    )

    data class Content(
        val text: String
    )

    fun toOpenAiRecommendation(): OpenAiRecommendation {
        val modelContent = this.output.first().content.first().text
        return jacksonObjectMapper().readValue<OpenAiRecommendation>(modelContent)

    }
}