package kr.motung_i.backend.domain.tour.usecase.impl

import kr.motung_i.backend.domain.user.usecase.FetchCurrentUserUsecase
import kr.motung_i.backend.global.exception.CustomException
import kr.motung_i.backend.global.exception.enums.CustomErrorCode
import kr.motung_i.backend.global.third_party.naver.NaverAdapter
import kr.motung_i.backend.persistence.tour.entity.Location
import kr.motung_i.backend.persistence.tour.repository.TourRepository
import kr.motung_i.backend.persistence.user.entity.User
import kr.motung_i.backend.testfixture.GlobalFixture.createTestNaverDirectionsResponse
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
class FetchDriveRouteUsecaseImplTest {
    @Mock
    private lateinit var fetchCurrentUserUsecase: FetchCurrentUserUsecase
    @Mock
    private lateinit var naverAdapter: NaverAdapter
    @Mock
    private lateinit var tourRepository: TourRepository

    @InjectMocks
    private lateinit var fetchDriveRouteUsecaseImpl: FetchDriveRouteUsecaseImpl

    @Test
    @DisplayName("요청을 정상 처리하면 올바른 데이터를 반환한다")
    fun 요청을_정상_처리하면_올바른_데이터를_반환한다() {
        val user = createTestUser()
        val tour = createTestTour()
        val naverDirections = createTestNaverDirectionsResponse()

        val expectedTraoptimal = naverDirections.route.traoptimal.first()
        val expectedTraavoidtoll = naverDirections.route.traavoidtoll.first()

        given(fetchCurrentUserUsecase.execute()).willReturn(user)
        given(tourRepository.findByUserAndIsActive(any<User>(), any<Boolean>()))
            .willReturn(tour)
        given(naverAdapter.fetchDrivingRoute(any<Location>(), any<Location>()))
            .willReturn(naverDirections)

        val result = fetchDriveRouteUsecaseImpl.execute(0.0, 0.0)

        listOf(
            Pair(expectedTraoptimal, result.traoptimal),
            Pair(expectedTraavoidtoll, result.traavoidtoll)
        ).forEach { (expected, actual) ->
            assertAll(
                { assertEquals(expected.guide.map { it.instructions }, actual.guide) },
                { assertEquals(expected.summary.goal.location[0], actual.endLon) },
                { assertEquals(expected.summary.goal.location[1], actual.endLat) },
                { assertEquals(expected.summary.start.location[0], actual.startLon) },
                { assertEquals(expected.summary.start.location[1], actual.startLat) },
                { assertEquals(expected.summary.tollFare, actual.tollFare) },
                { assertEquals(expected.summary.taxiFare, actual.taxiFare) },
                { assertEquals(expected.summary.duration, actual.duration) },
                { assertEquals(expected.summary.distance, actual.distance) },
                { assertEquals(expected.path.first().first(), actual.path.first().first()) },
                { assertEquals(expected.section.first().congestion, actual.section.first().congestion) },
                { assertEquals(expected.section.first().pointCount, actual.section.first().pointCount) },
                { assertEquals(expected.section.first().pointIndex, actual.section.first().pointIndex) },
            )
        }
    }

    @Test
    @DisplayName("여행중인 상태가 아니라면 예외가 발생한다")
    fun 여행중인_상태가_아니라면_예외가_발생한다() {
        val user = createTestUser()

        given(fetchCurrentUserUsecase.execute()).willReturn(user)
        given(tourRepository.findByUserAndIsActive(any<User>(), any<Boolean>()))
            .willReturn(null)

        val throws = assertThrows(CustomException::class.java) {
            fetchDriveRouteUsecaseImpl.execute(0.0, 0.0)
        }

        assertEquals(CustomErrorCode.NOT_ACTIVATED_TOUR, throws.customErrorCode)
    }
}