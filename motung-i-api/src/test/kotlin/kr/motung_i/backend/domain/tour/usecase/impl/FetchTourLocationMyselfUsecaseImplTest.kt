package kr.motung_i.backend.domain.tour.usecase.impl

import kr.motung_i.backend.domain.user.usecase.FetchCurrentUserUsecase
import kr.motung_i.backend.global.exception.CustomException
import kr.motung_i.backend.global.exception.enums.CustomErrorCode
import kr.motung_i.backend.global.geojson.LocalsCache
import kr.motung_i.backend.global.util.MultiPolygonUtil.toList
import kr.motung_i.backend.persistence.tour.entity.Local
import kr.motung_i.backend.persistence.tour.repository.TourRepository
import kr.motung_i.backend.persistence.user.entity.User
import kr.motung_i.backend.testfixture.TourFixture
import kr.motung_i.backend.testfixture.TourFixture.createTestTour
import kr.motung_i.backend.testfixture.UserFixture.createTestUser
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.any
import org.mockito.kotlin.given
import kotlin.test.assertEquals

@ExtendWith(MockitoExtension::class)
class FetchTourLocationMyselfUsecaseImplTest {
    @Mock
    private lateinit var tourRepository: TourRepository

    @Mock
    private lateinit var fetchCurrentUserUsecase: FetchCurrentUserUsecase

    @Mock
    private lateinit var localsCache: LocalsCache

    @InjectMocks
    private lateinit var fetchTourLocationMyselfUsecaseImpl: FetchTourLocationMyselfUsecaseImpl

    @Test
    @DisplayName("요청을 정상적으로 처리하면 올바른 데이터를 반환한다")
    fun 요청을_정상적으로_처리하면_올바른_데이터를_반환한다() {
        val user = createTestUser()
        val tour = createTestTour()
        val geometry = TourFixture.createTestGeometry()

        val expectedLon = tour.location.lon
        val expectedLat = tour.location.lat
        val expectedLocalAlias = tour.local.localAlias
        val expectedGeometry = geometry.toList()

        given(fetchCurrentUserUsecase.execute()).willReturn(user)
        given(tourRepository.findByUserAndIsActive(any<User>(), any<Boolean>()))
            .willReturn(tour)
        given(localsCache.findGeometryByLocal(any<Local>())).willReturn(geometry)

        val result = fetchTourLocationMyselfUsecaseImpl.execute()

        assertAll(
            { assertEquals(expectedLon, result.lon) },
            { assertEquals(expectedLat, result.lat) },
            { assertEquals(expectedLocalAlias, result.local) },
            { assertEquals(expectedGeometry, result.geometry.coordinates) },
        )
    }

    @Test
    @DisplayName("여행중인 상태가 아니라면 예외가 발생한다")
    fun 여행중인_상태가_아니라면_예외가_발생한다() {
        val user = createTestUser()

        given(fetchCurrentUserUsecase.execute()).willReturn(user)
        given(tourRepository.findByUserAndIsActive(any<User>(), any<Boolean>()))
            .willReturn(null)

        val throws = assertThrows(CustomException::class.java) {
            fetchTourLocationMyselfUsecaseImpl.execute()
        }

        assertEquals(CustomErrorCode.NOT_ACTIVATED_TOUR, throws.customErrorCode)
    }

    @Test
    @DisplayName("여행중인_지역의_지리정보가_없으면_예외가_발생한다")
    fun 여행중인_지역의_지리정보가_없으면_예외가_발생한다() {
        val user = createTestUser()
        val tour = createTestTour()

        given(fetchCurrentUserUsecase.execute()).willReturn(user)
        given(tourRepository.findByUserAndIsActive(any<User>(), any<Boolean>()))
            .willReturn(tour)
        given(localsCache.findGeometryByLocal(any<Local>())).willReturn(null)

        val throws = assertThrows(CustomException::class.java) {
            fetchTourLocationMyselfUsecaseImpl.execute()
        }

        assertEquals(CustomErrorCode.NOT_FOUND_LOCAL, throws.customErrorCode)
    }
}