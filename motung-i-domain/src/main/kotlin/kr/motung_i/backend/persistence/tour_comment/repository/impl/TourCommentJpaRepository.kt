package kr.motung_i.backend.persistence.tour_comment.repository.impl

import kr.motung_i.backend.persistence.tour.entity.Tour
import kr.motung_i.backend.persistence.tour_comment.entity.TourComment
import org.springframework.data.jpa.repository.JpaRepository

interface TourCommentJpaRepository: JpaRepository<TourComment, Long> {
    fun findByTour(tour: Tour): TourComment?
    fun existsByTour(tour: Tour): Boolean
}