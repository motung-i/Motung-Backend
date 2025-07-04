package kr.motung_i.backend.domain.review.usecase.impl

import kr.motung_i.backend.domain.user.usecase.FetchCurrentUserUsecase
import kr.motung_i.backend.persistence.review.repository.ReviewRepository
import kr.motung_i.backend.persistence.user.entity.User
import kr.motung_i.backend.testfixture.ReviewFixture.createTestReview
import kr.motung_i.backend.testfixture.UserFixture.createTestUser
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.any
import org.mockito.kotlin.given

@ExtendWith(MockitoExtension::class)
class FetchMyReviewsUsecaseImplTest {
    @Mock
    private lateinit var fetchCurrentUserUsecase: FetchCurrentUserUsecase
    @Mock
    private lateinit var reviewRepository: ReviewRepository

    @InjectMocks
    private lateinit var fetchMyReviewsUsecaseImpl: FetchMyReviewsUsecaseImpl

    @Test
    @DisplayName("자신의 리뷰를 조회하면 올바른 DTO 형식으로 반환한다")
    fun 자신의_리뷰를_조회하면_올바른_DTO_형식으로_반환한다() {
        val user = createTestUser(nickName = "testNickName")
        val reviews = listOf(createTestReview(user = user), createTestReview(user = user))

        given(fetchCurrentUserUsecase.execute()).willReturn(user)
        given(reviewRepository.findWithUserByUserOrderByCreatedAt(any<User>()))
            .willReturn(reviews)

        val result = fetchMyReviewsUsecaseImpl.execute()

        reviews.zip(result.reviews).forEach { (review, dto) ->
            assertAll(
                { assertEquals(review.id, dto.reviewId) },
                { assertEquals(review.local.localAlias, dto.local) },
                { assertEquals(review.imageUrls.firstOrNull(), dto.imageUrls.firstOrNull()) },
                { assertEquals(review.description, dto.description) },
                { assertEquals(review.isRecommend, dto.isRecommend) },
                { assertEquals(user.nickname, dto.nickname) },
            )
        }
    }
}