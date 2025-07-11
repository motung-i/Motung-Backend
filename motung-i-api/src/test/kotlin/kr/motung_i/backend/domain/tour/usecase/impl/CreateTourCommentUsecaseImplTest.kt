package kr.motung_i.backend.domain.tour.usecase.impl

import kr.motung_i.backend.domain.user.usecase.FetchCurrentUserUsecase
import kr.motung_i.backend.global.exception.CustomException
import kr.motung_i.backend.global.exception.enums.CustomErrorCode
import kr.motung_i.backend.global.third_party.open_ai.OpenAiAdapter
import kr.motung_i.backend.persistence.tour.entity.Tour
import kr.motung_i.backend.persistence.tour.repository.TourRepository
import kr.motung_i.backend.persistence.tour_comment.repository.TourCommentRepository
import kr.motung_i.backend.persistence.user.entity.User
import kr.motung_i.backend.testfixture.GlobalFixture.createTestModelContentResponse
import kr.motung_i.backend.testfixture.TourFixture.createTestTour
import kr.motung_i.backend.testfixture.UserFixture.createTestUser
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.any
import org.mockito.kotlin.check
import org.mockito.kotlin.given
import org.mockito.kotlin.then
import kotlin.test.Test

@ExtendWith(MockitoExtension::class)
class CreateTourCommentUsecaseImplTest {
    @Mock
    private lateinit var openAiAdapter: OpenAiAdapter
    @Mock
    private lateinit var tourCommentRepository: TourCommentRepository
    @Mock
    private lateinit var tourRepository: TourRepository
    @Mock
    private lateinit var fetchCurrentUserUsecase: FetchCurrentUserUsecase

    @InjectMocks
    private lateinit var createTourCommentUsecaseImpl: CreateTourCommentUsecaseImpl

    @Test
    @DisplayName("정상적으로 처리하면 올바른 데이터로 저장한다")
    fun 정상적으로_처리하면_올바른_데이터로_저장한다() {
        val user = createTestUser()
        val tour = createTestTour(isActive = true)
        val modelContent = createTestModelContentResponse()

        val expectedTourId = tour.id

        given(fetchCurrentUserUsecase.execute()).willReturn(user)
        given(tourRepository.findByUserAndIsActive(any<User>(), any<Boolean>())).willReturn(tour)
        given(tourCommentRepository.existsByTour(any<Tour>())).willReturn(false)
        given(openAiAdapter.createModelContent(any<String>())).willReturn(modelContent)

        val openAiRecommendation = modelContent.toOpenAiRecommendation()
        val (restaurantComment, sightseeingSpotsComment, cultureComment) = openAiRecommendation.toFormattedComments()

        createTourCommentUsecaseImpl.execute()

        then(tourCommentRepository).should().save(check {
            assertAll(
                { assertEquals(expectedTourId, it.tour.id) },
                { assertEquals(restaurantComment, it.restaurantComment) },
                { assertEquals(sightseeingSpotsComment, it.sightseeingSpotsComment) },
                { assertEquals(cultureComment, it.cultureComment) },
            )
        })
    }

    @Test
    @DisplayName("여행중인 상태가 아니라면 예외가 발생한다")
    fun 여행중인_상태가_아니라면_예외가_발생한다() {
        val user = createTestUser()

        given(fetchCurrentUserUsecase.execute()).willReturn(user)
        given(tourRepository.findByUserAndIsActive(any<User>(), any<Boolean>()))
            .willReturn(null)

        val throws = assertThrows(CustomException::class.java) {
            createTourCommentUsecaseImpl.execute()
        }

        assertEquals(CustomErrorCode.NOT_ACTIVATED_TOUR, throws.customErrorCode)
    }

    @Test
    @DisplayName("생성된 코멘트가 존재하면 예외가 발생한다")
    fun 생성된_코멘트가_존재하면_예외가_발생한다() {
        val user = createTestUser()
        val tour = createTestTour(isActive = true)

        given(fetchCurrentUserUsecase.execute()).willReturn(user)
        given(tourRepository.findByUserAndIsActive(any<User>(), any<Boolean>())).willReturn(tour)
        given(tourCommentRepository.existsByTour(any<Tour>())).willReturn(true)

        val throws = assertThrows(CustomException::class.java) {
            createTourCommentUsecaseImpl.execute()
        }

        assertEquals(CustomErrorCode.ALREADY_EXISTS_TOUR_COMMENT, throws.customErrorCode)
    }
}