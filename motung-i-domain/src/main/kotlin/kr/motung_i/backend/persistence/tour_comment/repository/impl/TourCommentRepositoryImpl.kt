package kr.motung_i.backend.persistence.tour_comment.repository.impl

import kr.motung_i.backend.persistence.tour.entity.Tour
import kr.motung_i.backend.persistence.tour_comment.entity.TourComment
import kr.motung_i.backend.persistence.tour_comment.repository.TourCommentRepository
import org.springframework.stereotype.Repository

@Repository
class TourCommentRepositoryImpl(
    private val tourCommentJpaRepository: TourCommentJpaRepository,
): TourCommentRepository {
    override fun save(tourComment: TourComment) {
        tourCommentJpaRepository.save(tourComment)
    }

    override fun findByTour(tour: Tour): TourComment? =
        tourCommentJpaRepository.findByTour(tour)
}