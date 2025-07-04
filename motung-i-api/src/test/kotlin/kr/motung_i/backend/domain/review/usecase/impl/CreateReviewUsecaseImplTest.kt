package kr.motung_i.backend.domain.review.usecase.impl

import kr.motung_i.backend.domain.review.presentation.dto.request.CreateReviewRequest
import kr.motung_i.backend.domain.user.usecase.FetchCurrentUserUsecase
import kr.motung_i.backend.global.exception.CustomException
import kr.motung_i.backend.global.exception.enums.CustomErrorCode
import kr.motung_i.backend.global.third_party.s3.usecase.UploadImageUsecase
import kr.motung_i.backend.persistence.review.repository.ReviewRepository
import kr.motung_i.backend.persistence.tour.repository.TourRepository
import kr.motung_i.backend.persistence.user.entity.User
import kr.motung_i.backend.testfixture.TourFixture.createTestTour
import kr.motung_i.backend.testfixture.UserFixture.createTestUser
import org.junit.jupiter.api.Assertions.assertAll
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.any
import org.mockito.kotlin.check
import org.mockito.kotlin.given
import org.mockito.kotlin.never
import org.mockito.kotlin.then
import org.springframework.mock.web.MockMultipartFile
import org.springframework.web.multipart.MultipartFile

@ExtendWith(MockitoExtension::class)
class CreateReviewUsecaseImplTest {
    @Mock
    private lateinit var reviewRepository: ReviewRepository
    @Mock
    private lateinit var tourRepository: TourRepository
    @Mock
    private lateinit var fetchCurrentUserUsecase: FetchCurrentUserUsecase
    @Mock
    private lateinit var uploadImageUsecase: UploadImageUsecase

    @InjectMocks
    private lateinit var createReviewUsecaseImpl: CreateReviewUsecaseImpl

    @Test
    @DisplayName("리뷰를 저장하면 올바른 데이터로 저장한다")
    fun 리뷰를_저장하면_올바른_데이터로_저장한다() {
        val request = CreateReviewRequest(false, "testReview description")
        val image = MockMultipartFile("https://image", null)
        val user = createTestUser()
        val expectedUserId = user.id

        given(fetchCurrentUserUsecase.execute()).willReturn(user)
        given(tourRepository.findByUserAndIsActive(any<User>(), any<Boolean>()))
            .willReturn(createTestTour(user = user))
        given(uploadImageUsecase.execute(any<MultipartFile>()))
            .willReturn(image.name)

        createReviewUsecaseImpl.execute(listOf(image), request)

        then(reviewRepository).should().save(check {
            assertAll(
                { assertEquals(expectedUserId, it.user.id) },
                { assertEquals(request.description, it.description) },
                { assertEquals(request.isRecommend, it.isRecommend) },
                { assertEquals(image.name, it.imageUrls.firstOrNull()) },
            )
        })
    }

    @Test
    @DisplayName("여행하는중이 아닌 상태에서 요청하면 예외가 발생한다")
    fun 여행하는중이_아닌_상태에서_요청하면_예외가_발생한다() {
        val request = CreateReviewRequest(false, "testReview description")
        val user = createTestUser()

        given(fetchCurrentUserUsecase.execute()).willReturn(user)
        given(tourRepository.findByUserAndIsActive(any<User>(), any<Boolean>()))
            .willReturn(null)
        val throws = assertThrows<CustomException> {
            createReviewUsecaseImpl.execute(listOf(), request)
        }

        assertEquals(CustomErrorCode.NOT_ACTIVATED_TOUR, throws.customErrorCode)
    }

    @Test
    @DisplayName("저장할 이미지가 없다면 uploadImageUsecase를 실행하지 않는다")
    fun 저장할_이미지가_없다면_uploadImageUsecase를_실행하지_않는다() {
        val request = CreateReviewRequest(false, "testReview description")
        val user = createTestUser()

        given(fetchCurrentUserUsecase.execute()).willReturn(user)
        given(tourRepository.findByUserAndIsActive(any<User>(), any<Boolean>()))
            .willReturn(createTestTour())

        createReviewUsecaseImpl.execute(listOf(), request)

        then(uploadImageUsecase).should(never()).execute(any<MultipartFile>())
    }
}