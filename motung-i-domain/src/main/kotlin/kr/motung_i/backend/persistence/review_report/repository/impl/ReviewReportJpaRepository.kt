package kr.motung_i.backend.persistence.review_report.repository.impl

import kr.motung_i.backend.persistence.review.entity.Review
import kr.motung_i.backend.persistence.review_report.entity.ReviewReport
import kr.motung_i.backend.persistence.user.entity.User
import org.springframework.data.jpa.repository.JpaRepository

interface ReviewReportJpaRepository: JpaRepository<ReviewReport, Long> {
    fun findByReviewAndProposer(review: Review, proposer: User): ReviewReport?
    fun findByReview(review: Review): List<ReviewReport>
}