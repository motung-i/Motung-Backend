package kr.motung_i.backend.domain.review.usecase.impl

import kr.motung_i.backend.persistence.review.repository.ReviewRepository
import kr.motung_i.backend.persistence.tour.entity.Country
import kr.motung_i.backend.testfixture.ReviewFixture.createTestFetchReviewsRequest
import kr.motung_i.backend.testfixture.ReviewFixture.createTestReview
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.any
import org.mockito.kotlin.anyOrNull
import org.mockito.kotlin.given

@ExtendWith(MockitoExtension::class)
class FetchReviewsUsecaseImplTest {
    @Mock
    private lateinit var reviewRepository: ReviewRepository

    @InjectMocks
    private lateinit var fetchReviewsUsecaseImpl: FetchReviewsUsecaseImpl

    @Test
    @DisplayName("리뷰를 조회하면 올바른 DTO 형식으로 반환한다")
    fun 리뷰를_조회하면_올바른_DTO_형식으로_반환한다() {
        val request = createTestFetchReviewsRequest()
        val reviews = listOf(createTestReview(), createTestReview())

        given(reviewRepository.findWithUserByLocalAliasAndOnlyByImageAndOnlyByReportedOrderByCreatedAt(
            anyOrNull<Country>(), anyOrNull<String>(), anyOrNull<String>(),
            anyOrNull<String>(), anyOrNull<String>(),
            any<Boolean>(), any<Boolean>()
        )).willReturn(reviews)

        val result = fetchReviewsUsecaseImpl.execute(request)

        reviews.zip(result.reviews).forEach { (review, dto) ->
            assertAll(
                { assertEquals(review.id, dto.reviewId) },
                { assertEquals(review.local.localAlias, dto.local) },
                { assertEquals(review.imageUrls.firstOrNull(), dto.imageUrls.firstOrNull()) },
                { assertEquals(review.description, dto.description) },
                { assertEquals(review.isRecommend, dto.isRecommend) },
                { assertEquals(review.user.nickname, dto.nickname) },
            )
        }
    }
}