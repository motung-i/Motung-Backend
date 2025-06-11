package kr.motung_i.backend.domain.tour.presentation.dto.response

import kr.motung_i.backend.persistence.tour_comment.entity.TourComment

data class FetchTourCommentMyselfResponse(
    val restaurantComment: String,
    val sightseeingSpotsComment: String,
    val cultureComment: String,
) {
    companion object {
        fun toDto(tourComment: TourComment): FetchTourCommentMyselfResponse =
            FetchTourCommentMyselfResponse(
                restaurantComment = tourComment.restaurantComment,
                sightseeingSpotsComment = tourComment.sightseeingSpotsComment,
                cultureComment = tourComment.cultureComment,
            )
    }
}