package kr.motung_i.backend.domain.tour.usecase.impl

import kr.motung_i.backend.domain.user.usecase.FetchCurrentUserUsecase
import kr.motung_i.backend.global.exception.CustomException
import kr.motung_i.backend.global.exception.enums.CustomErrorCode
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

@ExtendWith(MockitoExtension::class)
class CreateTourUsecaseImplTest {
    @Mock
    private lateinit var tourRepository: TourRepository
    @Mock
    private lateinit var fetchCurrentUserUsecase: FetchCurrentUserUsecase

    @InjectMocks
    lateinit var createTourUsecase: CreateTourUsecaseImpl

    @Test
    @DisplayName("요청을 정상처리하면 여행중 상태로 변경한다")
    fun 요청을_정상처리하면_여행중_상태로_변경한다() {
        val user = createTestUser()
        val tour = createTestTour()

        given(fetchCurrentUserUsecase.execute()).willReturn(user)
        given(tourRepository.findByUser(any<User>())).willReturn(tour)

        createTourUsecase.execute()

        assertEquals(tour.isActive, true)
    }

    @Test
    @DisplayName("뽑은 여행지가 없다면 예외가 발생한다")
    fun 뽑은_여행지가_없다면_예외가_발생한다() {
        val user = createTestUser()

        given(fetchCurrentUserUsecase.execute()).willReturn(user)
        given(tourRepository.findByUser(any<User>())).willReturn(null)

        val throws = assertThrows(CustomException::class.java) {
            createTourUsecase.execute()
        }

        assertEquals(CustomErrorCode.NOT_FOUND_TOUR, throws.customErrorCode)
    }

    @Test
    @DisplayName("이미 여행중인 상태라면 예외가 발생한다")
    fun 이미_여행중인_상태라면_예외가_발생한다() {
        val user = createTestUser()
        val tour = createTestTour(isActive = true)

        given(fetchCurrentUserUsecase.execute()).willReturn(user)
        given(tourRepository.findByUser(any<User>()))
            .willReturn(tour)

        val throws = assertThrows(CustomException::class.java) {
            createTourUsecase.execute()
        }

        assertEquals(CustomErrorCode.ALREADY_EXISTS_TOUR, throws.customErrorCode)
    }
}