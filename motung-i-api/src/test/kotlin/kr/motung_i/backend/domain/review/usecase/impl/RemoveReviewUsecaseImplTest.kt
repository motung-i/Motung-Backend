package kr.motung_i.backend.domain.review.usecase.impl

import kr.motung_i.backend.domain.review.presentation.dto.request.RemoveReviewRequest
import kr.motung_i.backend.domain.user.usecase.SuspensionUserUsecase
import kr.motung_i.backend.global.exception.CustomException
import kr.motung_i.backend.global.exception.enums.CustomErrorCode
import kr.motung_i.backend.persistence.review.repository.ReviewRepository
import kr.motung_i.backend.persistence.review_report.entity.ReportReason
import kr.motung_i.backend.persistence.review_report.repository.ReviewReportRepository
import kr.motung_i.backend.persistence.user.entity.User
import kr.motung_i.backend.persistence.user_suspension.entity.SuspensionPeriod
import kr.motung_i.backend.persistence.user_suspension.entity.SuspensionTarget
import kr.motung_i.backend.testfixture.ReviewFixture.createTestReview
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.any
import org.mockito.kotlin.given
import org.mockito.kotlin.then
import java.util.UUID

@ExtendWith(MockitoExtension::class)
class RemoveReviewUsecaseImplTest {
    @Mock
    private lateinit var reviewRepository: ReviewRepository
    @Mock
    private lateinit var reviewReportRepository: ReviewReportRepository
    @Mock
    private lateinit var suspensionUserUsecase: SuspensionUserUsecase

    @InjectMocks
    private lateinit var removeReviewUsecaseImpl: RemoveReviewUsecaseImpl

    @Test
    @DisplayName("리뷰를 정상 삭제하면 delete를 호출한다")
    fun 리뷰를_정상_삭제하면_delete를_호출한다() {
        val request = RemoveReviewRequest(null)
        val review = createTestReview()

        given(reviewRepository.findById(any<UUID>())).willReturn(review)

        removeReviewUsecaseImpl.execute(review.id!!, request)

        then(reviewRepository).should().delete(review)
    }

    @Test
    @DisplayName("리뷰를 정상 삭제하면 suspensionUserUsecase를 호출한다")
    fun 리뷰를_정상_삭제하면_suspensionUserUsecase를_호출한다() {
        val request = RemoveReviewRequest(SuspensionPeriod.entries.random())
        val review = createTestReview()

        given(reviewRepository.findById(any<UUID>())).willReturn(review)

        removeReviewUsecaseImpl.execute(review.id!!, request)

        then(suspensionUserUsecase).should().execute(
            any<User>(),
            any<SuspensionTarget>(),
            any<Set<ReportReason>>(),
            any<SuspensionPeriod>()
        )
    }

    @Test
    @DisplayName("존재하지 않는 리뷰를 요청하면 예외가 발생한다")
    fun 존재하지_않는_리뷰를_요청하면_예외가_발생한다() {
        val notExistReviewId = UUID.randomUUID()
        val request = RemoveReviewRequest(null)

        given(reviewRepository.findById(notExistReviewId)).willReturn(null)

        val throws = assertThrows<CustomException> {
            removeReviewUsecaseImpl.execute(notExistReviewId, request)
        }

        assertEquals(CustomErrorCode.NOT_FOUND_REVIEW, throws.customErrorCode)
    }
}