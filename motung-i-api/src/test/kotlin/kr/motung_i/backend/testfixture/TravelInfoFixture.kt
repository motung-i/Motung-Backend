package kr.motung_i.backend.testfixture

import kr.motung_i.backend.persistence.travel_info.entity.TravelInfo
import java.util.UUID

object TravelInfoFixture {
    fun createTravelInfo(
        id: UUID = UUID.randomUUID(),
        imageUrl: String = "https://image/travelInfo",
        title: String = "testTravelInfo",
        description: String = "testTravelInfo description",
    ) = TravelInfo(
        id = id,
        imageUrl = imageUrl,
        title = title,
        description = description,
    )
}