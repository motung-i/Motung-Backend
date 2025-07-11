package kr.motung_i.backend.domain.tour.usecase.impl

import kr.motung_i.backend.domain.tour.usecase.dto.GeoLocation
import kr.motung_i.backend.domain.user.usecase.FetchCurrentUserUsecase
import kr.motung_i.backend.global.exception.CustomException
import kr.motung_i.backend.global.exception.enums.CustomErrorCode
import kr.motung_i.backend.persistence.tour.entity.Tour
import kr.motung_i.backend.persistence.tour.repository.TourRepository
import kr.motung_i.backend.testfixture.TourFixture.createTestLocal
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
import org.mockito.kotlin.check
import org.mockito.kotlin.given
import org.mockito.kotlin.then

@ExtendWith(MockitoExtension::class)
class CreateTourLocationUsecaseImplTest {
    @Mock
    private lateinit var fetchCurrentUserUsecase: FetchCurrentUserUsecase

    @Mock
    private lateinit var tourRepository: TourRepository

    @InjectMocks
    private lateinit var createTourLocationUsecaseImpl: CreateTourLocationUsecaseImpl

    @Test
    @DisplayName("요청을 정상처리하면 여행지를 올바른 데이터로 저장한다")
    fun 요청을_정상처리하면_여행지를_올바른_데이터로_저장한다() {
        val user = createTestUser()
        val local = createTestLocal()
        val geoLocation = GeoLocation(0.0, 0.0)

        val expectedUserId = user.id
        val expectedCountry = local.country
        val expectedLocalAlias = local.localAlias
        val expectedRegionAlias = local.regionAlias
        val expectedDistrictAlias = local.districtAlias
        val expectedNeighborhood = local.neighborhood
        val expectedLon = geoLocation.lon
        val expectedLat = geoLocation.lat

        given(fetchCurrentUserUsecase.execute()).willReturn(user)
        given(tourRepository.findByUser(user)).willReturn(null)

        createTourLocationUsecaseImpl.execute(local, geoLocation)

        then(tourRepository).should().save(check {
            assertAll(
                { assertEquals(expectedUserId, it.user.id) },
                { assertEquals(expectedCountry, it.local.country) },
                { assertEquals(expectedLocalAlias, it.local.localAlias) },
                { assertEquals(expectedRegionAlias, it.local.regionAlias) },
                { assertEquals(expectedNeighborhood, it.local.neighborhood) },
                { assertEquals(expectedDistrictAlias, it.local.districtAlias) },
                { assertEquals(expectedLon, it.location.lon) },
                { assertEquals(expectedLat, it.location.lat) },
            )
        })
    }

    @Test
    @DisplayName("이미 여행지를 뽑은 상태라면 save를 호출하여 덮어씌운다")
    fun 이미_여행지를_뽑은_상태라면_save를_호출하여_덮어씌운다() {
        val user = createTestUser()
        val tour = createTestTour(user = user)
        val local = createTestLocal()
        val geoLocation = GeoLocation(0.0, 0.0)

        given(fetchCurrentUserUsecase.execute()).willReturn(user)
        given(tourRepository.findByUser(user)).willReturn(tour)

        createTourLocationUsecaseImpl.execute(local, geoLocation)

        then(tourRepository).should().save(any<Tour>())
    }

    @Test
    @DisplayName("이미 여행중인 상태라면 예외가 발생한다")
    fun 이미_여행중인_상태라면_예외가_발생한다() {
        val user = createTestUser()
        val tour = createTestTour(user = user, isActive = true)
        val local = createTestLocal()
        val geoLocation = GeoLocation(0.0, 0.0)

        given(fetchCurrentUserUsecase.execute()).willReturn(user)
        given(tourRepository.findByUser(user)).willReturn(tour)

        val throws = assertThrows(CustomException::class.java) {
            createTourLocationUsecaseImpl.execute(local, geoLocation)
        }

        assertEquals(CustomErrorCode.ALREADY_EXISTS_TOUR, throws.customErrorCode)
    }
}