package kr.motung_i.backend.persistence.review_report.repository.impl

import kr.motung_i.backend.persistence.review.entity.Review
import kr.motung_i.backend.persistence.review_report.entity.ReviewReport
import kr.motung_i.backend.persistence.review_report.repository.ReviewReportRepository
import kr.motung_i.backend.persistence.user.entity.User
import org.springframework.stereotype.Repository

@Repository
class ReviewReportRepositoryImpl(
    private val reviewReportJpaRepository: ReviewReportJpaRepository,
): ReviewReportRepository {
    override fun save(reviewReport: ReviewReport) {

        reviewReportJpaRepository.save(reviewReport)
    }

    override fun findAll(): List<ReviewReport> =
        reviewReportJpaRepository.findAll()

    override fun findByReviewAndProposer(review: Review, proposer: User): ReviewReport? =
        reviewReportJpaRepository.findByReviewAndProposer(review, proposer)

    override fun findByReview(review: Review): List<ReviewReport> =
        reviewReportJpaRepository.findByReview(review)
}