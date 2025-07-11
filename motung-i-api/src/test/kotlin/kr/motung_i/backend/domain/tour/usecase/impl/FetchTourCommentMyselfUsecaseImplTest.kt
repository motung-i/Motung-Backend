package kr.motung_i.backend.domain.tour.usecase.impl

import kr.motung_i.backend.domain.user.usecase.FetchCurrentUserUsecase
import kr.motung_i.backend.persistence.tour.repository.TourRepository
import kr.motung_i.backend.persistence.tour_comment.repository.TourCommentRepository
import kr.motung_i.backend.persistence.user.entity.User
import kr.motung_i.backend.testfixture.TourFixture
import kr.motung_i.backend.testfixture.TourFixture.createTestTour
import kr.motung_i.backend.testfixture.UserFixture.createTestUser
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.any
import org.mockito.kotlin.given
import kotlin.test.assertEquals

@ExtendWith(MockitoExtension::class)
class FetchTourCommentMyselfUsecaseImplTest {
    @Mock
    private lateinit var tourRepository: TourRepository
    @Mock
    private lateinit var tourCommentRepository: TourCommentRepository
    @Mock
    private lateinit var fetchCurrentUserUsecase: FetchCurrentUserUsecase

    @InjectMocks
    private lateinit var fetchTourCommentMyselfUsecaseImpl: FetchTourCommentMyselfUsecaseImpl

    @Test
    @DisplayName("요청을 정상처리하면 올바른 데이터를 반환한다")
    fun 요청을_정상처리하면_올바른_데이터를_반환한다() {
        val user = createTestUser()
        val tour = createTestTour()
        val modelContent = TourFixture.createTestTourComment(tour = tour)

        val expectedRestaurantComment = modelContent.restaurantComment
        val expectedSightseeingSpotsComment = modelContent.sightseeingSpotsComment
        val expectedCultureComment = modelContent.cultureComment

        given(fetchCurrentUserUsecase.execute()).willReturn(user)
        given(tourRepository.findByUserAndIsActive(any<User>(), any<Boolean>()))
            .willReturn(tour)
        given(tourCommentRepository.findByTour(tour)).willReturn(modelContent)

        val result = fetchTourCommentMyselfUsecaseImpl.execute()

        assertAll(
            { assertEquals(expectedRestaurantComment, result.restaurantComment) },
            { assertEquals(expectedSightseeingSpotsComment, result.sightseeingSpotsComment) },
            { assertEquals(expectedCultureComment, result.cultureComment) },
        )
    }
}