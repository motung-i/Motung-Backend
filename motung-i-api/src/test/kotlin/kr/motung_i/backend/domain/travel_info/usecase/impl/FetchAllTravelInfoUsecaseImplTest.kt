package kr.motung_i.backend.domain.travel_info.usecase.impl

import kr.motung_i.backend.persistence.travel_info.repository.TravelInfoRepository
import kr.motung_i.backend.testfixture.TravelInfoFixture.createTravelInfo
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.given

@ExtendWith(MockitoExtension::class)
class FetchAllTravelInfoUsecaseImplTest {
    @Mock
    private lateinit var travelInfoRepository: TravelInfoRepository

    @InjectMocks
    private lateinit var fetchAllTravelInfoUsecaseImpl: FetchAllTravelInfoUsecaseImpl

    @Test
    @DisplayName("여행지 정보를 조회하면 올바른 DTO로 반환한다")
    fun 여행지_정보를_조회하면_올바른_DTO로_반환한다() {
        val travelInfos = listOf(createTravelInfo(), createTravelInfo())
        given(travelInfoRepository.findAll()).willReturn(travelInfos)

        val result = fetchAllTravelInfoUsecaseImpl.execute()

        travelInfos.zip(result.travelInfoList) { travelInfo, dto ->
            assertEquals(travelInfo.id, dto.id)
            assertEquals(travelInfo.title, dto.title)
            assertEquals(travelInfo.imageUrl, dto.imageUrl)
            assertEquals(travelInfo.description, dto.description)
        }
    }

}