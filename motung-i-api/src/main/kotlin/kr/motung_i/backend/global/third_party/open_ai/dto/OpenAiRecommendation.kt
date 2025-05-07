package kr.motung_i.backend.global.third_party.open_ai.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class OpenAiRecommendation(
    @JsonProperty("re")
    val restaurants: List<Recommendation>,

    @JsonProperty("ca")
    val cafesOrDessertPlaces: List<Recommendation>,

    @JsonProperty("na")
    val natureOrSightseeingSpots: List<Recommendation>,

    @JsonProperty("cu")
    val culturalExperiences: List<Recommendation>,
) {
    data class Recommendation(
        @JsonProperty("n")
        val name: String,

        @JsonProperty("d")
        val description: String,
    )
}
