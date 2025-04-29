package kr.motung_i.backend.domain.review.usecase

import kr.motung_i.backend.domain.review.presentation.dto.request.RemoveReviewRequest
import kr.motung_i.backend.domain.user.presentation.usecase.FetchCurrentUserUsecase
import kr.motung_i.backend.global.exception.CustomException
import kr.motung_i.backend.global.exception.enums.CustomErrorCode
import kr.motung_i.backend.persistence.review.repository.ReviewRepository
import kr.motung_i.backend.persistence.review_report.repository.ReviewReportRepository
import kr.motung_i.backend.persistence.user_suspension.entity.UserSuspension
import kr.motung_i.backend.persistence.user_suspension.repository.UserSuspensionRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime
import java.util.*

@Service
@Transactional
class RemoveReviewUsecase(
    private val reviewRepository: ReviewRepository,
    private val reviewReportRepository: ReviewReportRepository,
    private val userSuspensionRepository: UserSuspensionRepository,
    private val fetchCurrentUserUsecase: FetchCurrentUserUsecase,
) {
    fun execute(reviewId: UUID, request: RemoveReviewRequest) {
        val review = reviewRepository.findById(reviewId) ?: throw CustomException(CustomErrorCode.NOT_FOUND_REVIEW)
        val reasons = reviewReportRepository.findByReview(review).flatMap { it.reasons }.toSet()

        if (request.suspensionPeriod != null) {
            userSuspensionRepository.save(
                UserSuspension(
                    user = review.user,
                    reasons = reasons,
                    suspensionPeriod = request.suspensionPeriod,
                    resumeAt = LocalDateTime.now().plus(request.suspensionPeriod.period),
                    suspendedBy = fetchCurrentUserUsecase.execute()
                )
            )
        }

        reviewRepository.delete(review)
    }
}