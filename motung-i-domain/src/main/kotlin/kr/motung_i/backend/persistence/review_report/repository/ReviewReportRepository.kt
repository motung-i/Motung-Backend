package kr.motung_i.backend.persistence.review_report.repository

import kr.motung_i.backend.persistence.review.entity.Review
import kr.motung_i.backend.persistence.review_report.entity.ReviewReport
import kr.motung_i.backend.persistence.user.entity.User

interface ReviewReportRepository {
    fun save(reviewReport: ReviewReport)

    fun findByReviewAndProposer(review: Review, proposer: User): ReviewReport?
}