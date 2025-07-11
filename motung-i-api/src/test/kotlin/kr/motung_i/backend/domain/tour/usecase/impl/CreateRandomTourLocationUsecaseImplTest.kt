package kr.motung_i.backend.domain.tour.usecase.impl

import kr.motung_i.backend.domain.tour.presentation.dto.response.GeometryResponse
import kr.motung_i.backend.domain.tour.usecase.CreateTourLocationUsecase
import kr.motung_i.backend.global.exception.CustomException
import kr.motung_i.backend.global.exception.enums.CustomErrorCode
import kr.motung_i.backend.global.geojson.LocalsCache
import kr.motung_i.backend.global.geojson.dto.GeoLocal
import kr.motung_i.backend.global.geojson.formatter.LocalFormatterService
import kr.motung_i.backend.global.util.MultiPolygonUtil.isPointIn
import kr.motung_i.backend.persistence.tour.entity.Country
import kr.motung_i.backend.testfixture.TourFixture.createTestGeoLocal
import kr.motung_i.backend.testfixture.TourFixture.createTestRegions
import org.geolatte.geom.crs.CoordinateReferenceSystems.WGS84
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.lenient
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.any
import org.mockito.kotlin.eq
import org.mockito.kotlin.given

@ExtendWith(MockitoExtension::class)
class CreateRandomTourLocationUsecaseImplTest {
    @Mock
    private lateinit var localsCache: LocalsCache

    @Mock
    private lateinit var localFormatterService: LocalFormatterService

    @Mock
    private lateinit var createTourLocationUsecase: CreateTourLocationUsecase

    @InjectMocks
    private lateinit var createRandomTourLocationUsecaseImpl: CreateRandomTourLocationUsecaseImpl

    @Test
    @DisplayName("캐시된 지리 정보를 받아오지 못하면 예외가 발생한다")
    fun 캐시된_지리_정보를_받아오지_못하면_예외가_발생한다() {
        given(localsCache.findLocalByCountry(any<Country>())).willReturn(null)

        val throws = assertThrows<CustomException> {
            createRandomTourLocationUsecaseImpl.execute(Country.entries.random(), listOf(), listOf())
        }

        assertEquals(CustomErrorCode.NOT_FOUND_COUNTRY_GEOJSON, throws.customErrorCode)
    }

    @Test
    @DisplayName("정상 처리되면 지역 정보에서 하나를 뽑아서 올바른 데이터를 반환한다")
    fun 정상_처리되면_지역_정보에서_하나를_뽑아서_올바른_데이터를_반환한다() {
        val geoLocal = createTestGeoLocal()

        val geoRegion = geoLocal.geoRegions.first()
        val geoDistrict = geoRegion.geoDistricts.first()
        val geoNeighborhood = geoDistrict.geoNeighborhoods.first()

        val localAlias = "$geoRegion, $geoDistrict"
        val expectedGeometry = GeometryResponse.fromMultiPolygon(geoNeighborhood.geometry)

        given(localsCache.findLocalByCountry(any<Country>())).willReturn(geoLocal)
        given(localFormatterService.formatToLocalAlias(any<String>(), any<Country>()))
            .willReturn(localAlias)
        given(localFormatterService.formatToRegionAlias(any<String>(), any<Country>()))
            .willReturn(geoRegion.alias)

        val result = createRandomTourLocationUsecaseImpl.execute(
            geoLocal.country,
            listOf(),
            listOf()
        )

        assertAll(
            { assertEquals(localAlias, result.local) },
            { assertEquals(expectedGeometry, result.geometry) },
            { assertTrue(geoNeighborhood.geometry.isPointIn(result.lon, result.lat, WGS84)) }
        )
    }

    @Test
    @DisplayName("지역을 필터링 하면 해당 지역에서만 뽑는다")
    fun 지역을_필터링_하면_해당_지역에서만_뽑는다() {
        val geoRegions = createTestRegions()
        val geoLocal = createTestGeoLocal(geoRegions = geoRegions)

        val geoRegionA = geoLocal.geoRegions.first()
        val geoRegionB = geoLocal.geoRegions.last()

        setupLenientLocalFormatterMocks(geoLocal)
        given(localsCache.findLocalByCountry(any<Country>())).willReturn(geoLocal)
        given(localFormatterService.formatToRegionAlias(eq(geoRegionA.name), any<Country>()))
            .willReturn(geoRegionA.alias)
        given(localFormatterService.formatToRegionAlias(eq(geoRegionB.name), any<Country>()))
            .willReturn(geoRegionB.alias)

        val resultA = createRandomTourLocationUsecaseImpl.execute(
            geoLocal.country,
            listOf(geoRegionA.alias),
            listOf()
        )
        val resultB = createRandomTourLocationUsecaseImpl.execute(
            geoLocal.country,
            listOf(geoRegionB.alias),
            listOf()
        )

        assertEquals(geoRegionA.name, resultA.local.split(", ")[0])
        assertEquals(geoRegionB.name, resultB.local.split(", ")[0])
    }

    @Test
    @DisplayName("구역을 필터링 하면 해당 지역에서만 뽑는다")
    fun 구역을_필터링_하면_해당_지역에서만_뽑는다() {
        val geoRegions = createTestRegions()
        val geoLocal = createTestGeoLocal(geoRegions = geoRegions)

        val geoRegionA = geoLocal.geoRegions.first()
        val geoRegionB = geoLocal.geoRegions.last()
        val geoDistrictA = geoRegionA.geoDistricts.first()
        val geoDistrictB = geoRegionB.geoDistricts.last()

        setupLenientLocalFormatterMocks(geoLocal)
        given(localsCache.findLocalByCountry(any<Country>())).willReturn(geoLocal)
        given(localFormatterService.formatToRegionAlias(eq(geoRegionA.name), any<Country>()))
            .willReturn(geoRegionA.alias)
        given(localFormatterService.formatToRegionAlias(eq(geoRegionB.name), any<Country>()))
            .willReturn(geoRegionB.alias)

        val resultA = createRandomTourLocationUsecaseImpl.execute(
            geoLocal.country,
            listOf(),
            listOf(geoDistrictA.alias)
        )
        val resultB = createRandomTourLocationUsecaseImpl.execute(
            geoLocal.country,
            listOf(),
            listOf(geoDistrictB.alias)
        )

        assertEquals(geoDistrictA.name, resultA.local.split(", ")[1])
        assertEquals(geoDistrictB.name, resultB.local.split(", ")[1])
    }

    private fun setupLenientLocalFormatterMocks(geoLocal: GeoLocal) {
        geoLocal.geoRegions.forEach { region ->
            region.geoDistricts.forEach { district ->
                district.geoNeighborhoods.forEach { geoNeighborhoods ->
                    val local = "${region.name} ${district.name} ${geoNeighborhoods.name}"
                    val localAlias = "${region.name}, ${district.name}"

                    lenient().`when`(localFormatterService.formatToLocalAlias(eq(local), any<Country>()))
                        .thenReturn(localAlias)
                }
            }
        }
    }
}