package kr.motung_i.backend.domain.travel_info.presentation.dto.response

import kr.motung_i.backend.persistence.travel_info.entity.TravelInfo
import java.util.UUID

data class TravelInfoResponse(
    val id: UUID?,
    val imageUrl: String,
    val title: String,
    val description: String,
) {
    companion object {
        fun from(travelInfo: TravelInfo): TravelInfoResponse =
            TravelInfoResponse(
                id = travelInfo.id,
                imageUrl = travelInfo.imageUrl,
                title = travelInfo.title,
                description = travelInfo.description,
            )
    }
}