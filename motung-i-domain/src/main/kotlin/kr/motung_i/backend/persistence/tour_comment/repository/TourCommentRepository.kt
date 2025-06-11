package kr.motung_i.backend.persistence.tour_comment.repository

import kr.motung_i.backend.persistence.tour.entity.Tour
import kr.motung_i.backend.persistence.tour_comment.entity.TourComment

interface TourCommentRepository {
    fun save(tourComment: TourComment)
    fun findByTour(tour: Tour): TourComment?
}