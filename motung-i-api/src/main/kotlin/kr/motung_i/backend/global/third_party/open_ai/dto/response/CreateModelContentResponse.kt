package kr.motung_i.backend.global.third_party.open_ai.dto.response

data class CreateModelContentResponse(
    val output: List<Output>
) {
    data class Output(
        val content: List<Content>,
    )

    data class Content(
        val text: String
    )
}