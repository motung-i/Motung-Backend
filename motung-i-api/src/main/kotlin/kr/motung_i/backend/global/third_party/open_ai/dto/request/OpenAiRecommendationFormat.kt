package kr.motung_i.backend.global.third_party.open_ai.dto.request

data class OpenAiRecommendationFormat(
    val format: Format = Format()
) {
    data class Format(
        val type: String = "json_schema",
        val name: String = "travel_recommendations",
        val schema: Schema = Schema(),
        val strict: Boolean = true,
    )

    data class Schema(
        val type: String = "object",
        val properties: Properties = Properties(),
        val required: List<String> = listOf("re", "ca", "na", "cu"),
        val additionalProperties: Boolean = false,
    )

    data class Properties(
        val re: Recommendation = Recommendation(),
        val ca: Recommendation = Recommendation(),
        val na: Recommendation = Recommendation(),
        val cu: Recommendation = Recommendation(),
    )

    data class Recommendation(
        val type: String = "array",
        val items: Items = Items(),
    )

    data class Items(
        val type: String = "object",
        val properties: ItemsProperties = ItemsProperties(),
        val required: List<String> = listOf("n", "d"),
        val additionalProperties: Boolean = false,
    )

    data class ItemsProperties(
        val n: Property = Property(),
        val d: Property = Property(),
    )

    data class Property(
        val type: String = "string",
    )
}