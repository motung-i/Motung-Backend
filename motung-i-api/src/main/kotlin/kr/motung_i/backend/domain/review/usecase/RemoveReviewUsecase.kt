package kr.motung_i.backend.domain.review.usecase

import kr.motung_i.backend.domain.review.presentation.dto.request.RemoveReviewRequest
import kr.motung_i.backend.domain.user.usecase.SuspensionUserUsecase
import kr.motung_i.backend.global.exception.CustomException
import kr.motung_i.backend.global.exception.enums.CustomErrorCode
import kr.motung_i.backend.persistence.review.repository.ReviewRepository
import kr.motung_i.backend.persistence.review_report.repository.ReviewReportRepository
import kr.motung_i.backend.persistence.user_suspension.entity.SuspensionTarget
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
@Transactional
class RemoveReviewUsecase(
    private val reviewRepository: ReviewRepository,
    private val reviewReportRepository: ReviewReportRepository,
    private val suspensionUserUsecase: SuspensionUserUsecase
) {
    fun execute(reviewId: UUID, request: RemoveReviewRequest) {
        val review = reviewRepository.findById(reviewId) ?: throw CustomException(CustomErrorCode.NOT_FOUND_REVIEW)

        suspensionUserUsecase.execute(
            user = review.user,
            target = SuspensionTarget.REVIEW,
            suspensionPeriod = request.suspensionPeriod,
            reasons = reviewReportRepository.findByReview(review).flatMap { it.reasons }.toSet()
        )

        reviewRepository.delete(review)
    }
}