package kr.motung_i.backend.domain.tour.usecase.impl

import kr.motung_i.backend.domain.tour.formatter.TourFormatterService
import kr.motung_i.backend.global.exception.CustomException
import kr.motung_i.backend.global.exception.enums.CustomErrorCode
import kr.motung_i.backend.global.geojson.LocalsCache
import kr.motung_i.backend.global.geojson.dto.GeoDistrict
import kr.motung_i.backend.global.geojson.dto.GeoRegion
import kr.motung_i.backend.persistence.tour.entity.Country
import kr.motung_i.backend.testfixture.TourFixture.createTestGeoDistrict
import kr.motung_i.backend.testfixture.TourFixture.createTestGeoLocal
import kr.motung_i.backend.testfixture.TourFixture.createTestGeoRegion
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.any
import org.mockito.kotlin.eq
import org.mockito.kotlin.given
import kotlin.test.assertEquals

@ExtendWith(MockitoExtension::class)
class FetchTourFilterDistrictUsecaseImplTest {
    @Mock
    private lateinit var localsCache: LocalsCache

    @Mock
    private lateinit var tourFormatterService: TourFormatterService

    @InjectMocks
    private lateinit var fetchTourFilterDistrictUsecaseImpl: FetchTourFilterDistrictUsecaseImpl

    @Test
    @DisplayName("요청을 정상 처리하면 올바른 데이터로 반환한다")
    fun 요청을_정상_처리하면_올바른_데이터로_반환한다() {
        val geoLocal = createTestGeoLocal()
        val country = Country.entries.random()

        val expectedType = "TestType"
        val expectedDistrictAlias = "TestName"
        given(localsCache.findLocalByCountry(any<Country>())).willReturn(geoLocal)
        given(tourFormatterService.formatToTourFilterDistrict(any<GeoDistrict>(), any<Country>()))
            .willReturn(Pair(expectedType, expectedDistrictAlias))

        val result = fetchTourFilterDistrictUsecaseImpl.execute(country, geoLocal.geoRegions.first().alias)

        assertEquals(expectedDistrictAlias, result.districts.first().district.first())
        assertEquals(expectedType, result.districts.first().type)
    }

    @Test
    @DisplayName("기타를 요청하면 포함되는 지역을 반환한다")
    fun 기타를_요청하면_포함되는_지역을_반환한다() {
        val geoLocal = createTestGeoLocal()
        val country = Country.entries.random()

        val expectedType = "TestType"
        val expectedDistrictAlias = "TestName"
        given(localsCache.findLocalByCountry(any<Country>())).willReturn(geoLocal)
        given(tourFormatterService.formatToTourFilterRegion(any<GeoRegion>(), any<Country>()))
            .willReturn(country.etc)
        given(tourFormatterService.formatToTourFilterCityRegion(any<GeoRegion>(), any<Country>()))
            .willReturn(Pair(expectedType, expectedDistrictAlias))

        val result = fetchTourFilterDistrictUsecaseImpl.execute(country, country.etc)

        assertEquals(expectedDistrictAlias, result.districts.first().district.first())
        assertEquals(expectedType, result.districts.first().type)
    }

    @Test
    @DisplayName("중복된 값을 조회하면 중복을 제거한다")
    fun 중복된_값을_조회하면_중복을_제거한다() {
        val geoLocal = createTestGeoLocal(geoRegions = listOf(createTestGeoRegion(), createTestGeoRegion()))
        val country = Country.entries.random()

        given(localsCache.findLocalByCountry(any<Country>())).willReturn(geoLocal)
        given(tourFormatterService.formatToTourFilterDistrict(any<GeoDistrict>(), any<Country>()))
            .willReturn(Pair("TestType", "TestName"))

        val result = fetchTourFilterDistrictUsecaseImpl.execute(country, geoLocal.geoRegions.first().alias)

        assertEquals(1, result.districts.first().district.size)
    }

    @Test
    @DisplayName("요청을 정상적으로 처리하면 가나다순으로 반환한다")
    fun 요청을_정상적으로_처리하면_가나다순으로_반환한다() {
        val expectedFirstDistrict = createTestGeoDistrict(name = "가")
        val expectedSecondDistrict = createTestGeoDistrict(name = "나")
        val expectedThirdDistrict = createTestGeoDistrict(name = "다")
        val expectedFourthDistrict = createTestGeoDistrict(name = "라")

        val geoLocal = createTestGeoLocal(
            geoRegions = listOf(
                createTestGeoRegion(
                    geoDistricts = listOf(
                        expectedFirstDistrict,
                        expectedThirdDistrict,
                        expectedSecondDistrict,
                        expectedFourthDistrict
                    )
                )
            )
        )
        val country = Country.entries.random()

        given(localsCache.findLocalByCountry(any<Country>())).willReturn(geoLocal)
        geoLocal.geoRegions.flatMap { it.geoDistricts }.forEach {
            given(tourFormatterService.formatToTourFilterDistrict(eq(it), any<Country>()))
                .willReturn(Pair("TestType", it.name))
        }

        val result = fetchTourFilterDistrictUsecaseImpl.execute(country, geoLocal.geoRegions.first().alias)

        val district = result.districts.first().district
        assertEquals(4, district.size)
        assertEquals(expectedFirstDistrict.name, district.elementAt(0))
        assertEquals(expectedSecondDistrict.name, district.elementAt(1))
        assertEquals(expectedThirdDistrict.name, district.elementAt(2))
        assertEquals(expectedFourthDistrict.name, district.elementAt(3))
    }

    @Test
    @DisplayName("요청한 국가의 지리정보가 없으면 예외가 발생한다")
    fun 요청한_국가의_지리정보가_없으면_예외가_발생한다() {
        val notExistingCountry = Country.entries.random()

        given(localsCache.findLocalByCountry(any<Country>())).willReturn(null)

        val throws = assertThrows(CustomException::class.java) {
            fetchTourFilterDistrictUsecaseImpl.execute(notExistingCountry, "TestName")
        }

        assertEquals(CustomErrorCode.NOT_FOUND_COUNTRY_GEOJSON, throws.customErrorCode)
    }
}