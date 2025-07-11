package kr.motung_i.backend.testfixture

import kr.motung_i.backend.global.third_party.naver.dto.NaverDirectionsResponse
import kr.motung_i.backend.global.third_party.open_ai.dto.response.CreateModelContentResponse

object GlobalFixture {
    fun createTestModelContentResponse(): CreateModelContentResponse {
        return CreateModelContentResponse(
            listOf(
                CreateModelContentResponse.Output(
                    listOf(
                        CreateModelContentResponse.Content(
                            "{\n" +
                                    "  \"re\": [\n" +
                                    "    { \"n\": \"reTestA\", \"d\": \"식당A\" },\n" +
                                    "    { \"n\": \"reTestB\", \"d\": \"식당B\" }\n" +
                                    "  ],\n" +
                                    "  \"na\": [\n" +
                                    "    { \"n\": \"naTestA\", \"d\": \"관광A\" },\n" +
                                    "    { \"n\": \"naTestB\", \"d\": \"관광B\" }\n" +
                                    "  ],\n" +
                                    "  \"cu\": [\n" +
                                    "    { \"n\": \"cuTestA\", \"d\": \"문화A\" },\n" +
                                    "    { \"n\": \"cuTestB\", \"d\": \"문화B\" }\n" +
                                    "  ]\n" +
                                    "}"
                        )
                    )
                )
            )
        )
    }

    fun createTestNaverDirectionsResponse(): NaverDirectionsResponse {
        val direction =
            NaverDirectionsResponse.Direction(
                guide = listOf(
                    NaverDirectionsResponse.Guide(
                        instructions = "직진",
                    )
                ),
                path = listOf(listOf(0.0, 0.1)),
                summary = NaverDirectionsResponse.Summary(
                    distance = 1,
                    duration = 1,
                    goal = NaverDirectionsResponse.Location(listOf(0.0, 0.0)),
                    start = NaverDirectionsResponse.Location(listOf(1.0, 1.0)),
                    taxiFare = 0,
                    tollFare = 0,
                ),
                section = listOf(
                    NaverDirectionsResponse.Section(
                        pointIndex = 0,
                        pointCount = 0,
                        congestion = 0,
                    ),
                ),
            )

        return NaverDirectionsResponse(
            NaverDirectionsResponse.Route(
                listOf(direction),
                listOf(direction),
            )
        )
    }
}