package kr.motung_i.backend.global.third_party.open_ai.dto.request

data class CreateModelContentRequest(
    val model: String = "gpt-4.1-mini",
    val input: List<OpenAiRecommendationInput>,
    val text: OpenAiRecommendationFormat = OpenAiRecommendationFormat(),
) {
    companion object {
        fun fromLocalAlias(localAlias: String): CreateModelContentRequest {
            return CreateModelContentRequest(
                input = listOf(OpenAiRecommendationInput.fromLocalAlias(localAlias))
            )
        }
    }

    data class OpenAiRecommendationInput(
        val role: String = "system",
        val content: String,
    ) {
        companion object {
            fun fromLocalAlias(localAlias: String): OpenAiRecommendationInput {
                return OpenAiRecommendationInput(
                    content = "You are a helpful assistant. Recommend up to two well-known, real places in ${localAlias}. Only use locations recognized by locals or shown on maps. Do not include fictional names. If you are unsure about a place’s existence, do not include it.\n" +
                            "\n" +
                            "Respond only in Korean, with no introduction. End all sentences with “~니다”.\n" +
                            "\n" +
                            "Output in JSON format using these keys: re = restaurants, na = natureOrSightseeingSpots, cu = culturalExperiences. Each item must include:\n" +
                            "- `n`: name\n" +
                            "- `d`: one-sentence description\n" +
                            "\n" +
                            "If there is nothing to recommend in a category, return an empty array."
                )
            }
        }
    }
}