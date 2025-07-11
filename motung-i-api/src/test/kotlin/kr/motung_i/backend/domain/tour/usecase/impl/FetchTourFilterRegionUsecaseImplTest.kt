package kr.motung_i.backend.domain.tour.usecase.impl

import kr.motung_i.backend.domain.tour.formatter.TourFormatterService
import kr.motung_i.backend.global.exception.CustomException
import kr.motung_i.backend.global.exception.enums.CustomErrorCode
import kr.motung_i.backend.global.geojson.LocalsCache
import kr.motung_i.backend.global.geojson.dto.GeoRegion
import kr.motung_i.backend.persistence.tour.entity.Country
import kr.motung_i.backend.testfixture.TourFixture.createTestGeoLocal
import kr.motung_i.backend.testfixture.TourFixture.createTestGeoRegion
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
class FetchTourFilterRegionUsecaseImplTest {
    @Mock
    private lateinit var localsCache: LocalsCache
    @Mock
    private lateinit var tourFormatterService: TourFormatterService

    @InjectMocks
    private lateinit var fetchTourFilterRegionUsecaseImpl: FetchTourFilterRegionUsecaseImpl

    @Test
    @DisplayName("요청을 정상적으로 처리하면 올바른 데이터를 반환한다")
    fun 요청을_정상적으로_처리하면_올바른_데이터를_반환한다() {
        val country = Country.entries.random()
        val local = createTestGeoLocal()

        val expectedRegion = "TestRegion"

        given(localsCache.findLocalByCountry(any<Country>())).willReturn(local)
        given(tourFormatterService.formatToTourFilterRegion(any<GeoRegion>(), any<Country>()))
            .willReturn(expectedRegion)

        val result = fetchTourFilterRegionUsecaseImpl.execute(country)

        assertEquals(country.etc, result.regions.first())
        assertEquals(expectedRegion, result.regions.last())
    }

    @Test
    @DisplayName("중복된 값을 조회하면 중복을 제거한다")
    fun 중복된_값을_조회하면_중복을_제거한다() {
        val geoLocal = createTestGeoLocal(geoRegions = listOf(createTestGeoRegion(), createTestGeoRegion()))
        val country = Country.entries.random()

        given(localsCache.findLocalByCountry(any<Country>())).willReturn(geoLocal)
        given(tourFormatterService.formatToTourFilterRegion(any<GeoRegion>(), any<Country>()))
            .willReturn("TestRegion")

        val result = fetchTourFilterRegionUsecaseImpl.execute(country)

        assertEquals(2, result.regions.size)
    }

    @Test
    @DisplayName("요청한 국가의 지리정보가 없으면 예외가 발생한다")
    fun 요청한_국가의_지리정보가_없으면_예외가_발생한다() {
        val notExistingCountry = Country.entries.random()

        given(localsCache.findLocalByCountry(any<Country>())).willReturn(null)

        val throws = assertThrows(CustomException::class.java) {
            fetchTourFilterRegionUsecaseImpl.execute(notExistingCountry)
        }

        assertEquals(CustomErrorCode.NOT_FOUND_COUNTRY_GEOJSON, throws.customErrorCode)
    }
}