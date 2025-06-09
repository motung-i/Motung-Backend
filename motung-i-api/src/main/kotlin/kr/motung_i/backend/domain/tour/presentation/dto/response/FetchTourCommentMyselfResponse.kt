package kr.motung_i.backend.domain.tour.presentation.dto.response

import kr.motung_i.backend.persistence.tour.entity.Tour

data class FetchTourCommentMyselfResponse(
    val restaurantComment: String,
    val sightseeingSpotsComment: String,
    val cultureComment: String,
) {
    companion object {
        fun toDto(tour: Tour): FetchTourCommentMyselfResponse =
            FetchTourCommentMyselfResponse(
                restaurantComment = tour.restaurantComment,
                sightseeingSpotsComment = tour.sightseeingSpotsComment,
                cultureComment = tour.cultureComment,
            )
    }
}