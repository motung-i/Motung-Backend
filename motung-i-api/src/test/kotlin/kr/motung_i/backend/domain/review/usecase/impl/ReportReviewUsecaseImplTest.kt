package kr.motung_i.backend.domain.review.usecase.impl

import kr.motung_i.backend.domain.review.presentation.dto.request.ReportReviewRequest
import kr.motung_i.backend.domain.user.usecase.FetchCurrentUserUsecase
import kr.motung_i.backend.global.exception.CustomException
import kr.motung_i.backend.global.exception.enums.CustomErrorCode
import kr.motung_i.backend.persistence.review.entity.Review
import kr.motung_i.backend.persistence.review.repository.ReviewRepository
import kr.motung_i.backend.persistence.review_report.entity.ReportReason
import kr.motung_i.backend.persistence.review_report.entity.ReviewReport
import kr.motung_i.backend.persistence.review_report.repository.ReviewReportRepository
import kr.motung_i.backend.persistence.user.entity.User
import kr.motung_i.backend.testfixture.ReviewFixture.createTestReview
import kr.motung_i.backend.testfixture.UserFixture.createTestUser
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.any
import org.mockito.kotlin.check
import org.mockito.kotlin.given
import org.mockito.kotlin.then
import java.util.UUID
import kotlin.test.assertEquals

@ExtendWith(MockitoExtension::class)
class ReportReviewUsecaseImplTest {
    @Mock
    private lateinit var fetchCurrentUserUsecase: FetchCurrentUserUsecase
    @Mock
    private lateinit var reviewReportRepository: ReviewReportRepository
    @Mock
    private lateinit var reviewRepository: ReviewRepository

    @InjectMocks
    private lateinit var reportReviewUsecaseImpl: ReportReviewUsecaseImpl

    @Test
    @DisplayName("자신이 신고하지 않은 리뷰를 신고하면 올바른 데이터로 저장한다")
    fun 자신이_신고하지_않은_리뷰를_신고하면_올바른_데이터로_저장한다() {
        val request = ReportReviewRequest(setOf(ReportReason.entries.random()))
        val review = createTestReview()
        val user = createTestUser()
        val expectedReviewId = review.id
        val expectedUserId = user.id

        given(reviewRepository.findById(any<UUID>())).willReturn(review)
        given(fetchCurrentUserUsecase.execute()).willReturn(user)
        given(reviewReportRepository.findByReviewAndProposer(any<Review>(), any<User>()))
            .willReturn(null)

        reportReviewUsecaseImpl.execute(review.id!!, request)

        then(reviewReportRepository).should().save(check {
            assertAll(
                { assertEquals(expectedReviewId, it.review.id) },
                { assertEquals(expectedUserId, it.proposer.id) },
                { assertEquals(request.reasons.first(), it.reasons.first()) },
            )
        })
    }

    @Test
    @DisplayName("자신이 신고한 리뷰를 다시 신고하면 기존 신고 기록에 사유를 추가한다")
    fun 자신이_신고한_리뷰를_다시_신고하면_기존_신고_기록에_사유를_추가한다() {
        val existingReason = ReportReason.entries.first()
        val newReason = ReportReason.entries.last()
        val request = ReportReviewRequest(setOf(newReason))

        val review = createTestReview()
        val user = createTestUser()
        val existingReport = ReviewReport.of(setOf(existingReason), review, user)

        given(reviewRepository.findById(any<UUID>())).willReturn(review)
        given(fetchCurrentUserUsecase.execute()).willReturn(user)
        given(reviewReportRepository.findByReviewAndProposer(any<Review>(), any<User>()))
            .willReturn(existingReport)

        reportReviewUsecaseImpl.execute(review.id!!, request)

        assertEquals(setOf(existingReason, newReason), existingReport.reasons)
    }

    @Test
    @DisplayName("존재하지 않는 리뷰를 요청하면 예외가 발생한다")
    fun 존재하지_않는_리뷰를_요청하면_예외가_발생한다() {
        val notExistReviewId = UUID.randomUUID()
        val request = ReportReviewRequest(setOf(ReportReason.entries.random()))

        given(reviewRepository.findById(notExistReviewId)).willReturn(null)

        val throws = assertThrows<CustomException> {
            reportReviewUsecaseImpl.execute(notExistReviewId, request)
        }

        assertEquals(CustomErrorCode.NOT_FOUND_REVIEW, throws.customErrorCode)
    }
}