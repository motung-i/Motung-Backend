package kr.motung_i.backend.domain.tour.usecase.impl

import kr.motung_i.backend.domain.user.usecase.FetchCurrentUserUsecase
import kr.motung_i.backend.global.exception.CustomException
import kr.motung_i.backend.global.exception.enums.CustomErrorCode
import kr.motung_i.backend.persistence.tour.entity.Tour
import kr.motung_i.backend.persistence.tour.repository.TourRepository
import kr.motung_i.backend.persistence.user.entity.User
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
import org.mockito.kotlin.then

@ExtendWith(MockitoExtension::class)
class DeleteTourUsecaseImplTest {
    @Mock
    private lateinit var tourRepository: TourRepository
    @Mock
    private lateinit var fetchCurrentUserUsecase: FetchCurrentUserUsecase

    @InjectMocks
    private lateinit var deleteTourUsecaseImpl: DeleteTourUsecaseImpl

    @Test
    @DisplayName("요청을_정상_처리하면_delete가_호출된다")
    fun 요청을_정상_처리하면_delete가_호출된다() {
        var user = createTestUser()
        val tour = createTestTour()

        given(fetchCurrentUserUsecase.execute()).willReturn(user)
        given(tourRepository.findByUserAndIsActive(any<User>(), any<Boolean>()))
            .willReturn(tour)

        deleteTourUsecaseImpl.execute()

        then(tourRepository).should().delete(any<Tour>())
    }

    @Test
    @DisplayName("여행중인 상태가 아니라면 예외가 발생한다")
    fun 여행중인_상태가_아니라면_예외가_발생한다() {
        var user = createTestUser()

        given(fetchCurrentUserUsecase.execute()).willReturn(user)
        given(tourRepository.findByUserAndIsActive(any<User>(), any<Boolean>()))
            .willReturn(null)

        val throws = assertThrows(CustomException::class.java) {
            deleteTourUsecaseImpl.execute()
        }

        assertEquals(CustomErrorCode.NOT_ACTIVATED_TOUR, throws.customErrorCode)
    }
}