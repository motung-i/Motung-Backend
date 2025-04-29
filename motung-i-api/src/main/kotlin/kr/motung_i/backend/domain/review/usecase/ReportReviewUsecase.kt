package kr.motung_i.backend.domain.review.usecase

import kr.motung_i.backend.domain.review.presentation.dto.request.ReportReviewRequest
import kr.motung_i.backend.domain.user.usecase.FetchCurrentUserUsecase
import kr.motung_i.backend.global.exception.CustomException
import kr.motung_i.backend.global.exception.enums.CustomErrorCode
import kr.motung_i.backend.persistence.review.repository.ReviewRepository
import kr.motung_i.backend.persistence.review_report.entity.ReviewReport
import kr.motung_i.backend.persistence.review_report.repository.ReviewReportRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
@Transactional
class ReportReviewUsecase(
    private val fetchCurrentUserUsecase: FetchCurrentUserUsecase,
    private val reviewReportRepository: ReviewReportRepository,
    private val reviewRepository: ReviewRepository,
) {
    fun execute(reviewId: UUID, request: ReportReviewRequest) {
        val review = reviewRepository.findById(reviewId) ?: throw CustomException(CustomErrorCode.NOT_FOUND_REVIEW)
        val currentUser = fetchCurrentUserUsecase.execute()
        val reviewReportByCurrentUser = reviewReportRepository.findByReviewAndProposer(review, currentUser)

        if(reviewReportByCurrentUser == null) {
            reviewReportRepository.save(
                ReviewReport.of(
                    reasons = request.reasons.toMutableSet(),
                    review = review,
                    proposer = currentUser,
                )
            )
        } else {
            reviewReportByCurrentUser.addReasons(request.reasons)
        }
    }

}