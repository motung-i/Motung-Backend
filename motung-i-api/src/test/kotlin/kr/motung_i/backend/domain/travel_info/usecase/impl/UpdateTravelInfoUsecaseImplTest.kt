package kr.motung_i.backend.domain.travel_info.usecase.impl

import kr.motung_i.backend.domain.travel_info.presentation.dto.request.UpdateTravelInfoRequest
import kr.motung_i.backend.global.third_party.s3.usecase.UpdateImageUsecase
import kr.motung_i.backend.persistence.travel_info.repository.TravelInfoRepository
import kr.motung_i.backend.testfixture.TravelInfoFixture.createTravelInfo
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
import org.springframework.mock.web.MockMultipartFile
import org.springframework.web.multipart.MultipartFile
import java.util.UUID

@ExtendWith(MockitoExtension::class)
class UpdateTravelInfoUsecaseImplTest {
    @Mock
    private lateinit var travelInfoRepository: TravelInfoRepository
    @Mock
    private lateinit var updateImageUsecase: UpdateImageUsecase

    @InjectMocks
    private lateinit var updateTravelInfoUsecase: UpdateTravelInfoUsecaseImpl

    @Test
    @DisplayName("정상적으로 요청을 처리하면 여행지 정보에 변경사항이 반영된다")
    fun 정상적으로_요청을_처리하면_여행지_정보에_변경사항이_반영된다() {
        val request = UpdateTravelInfoRequest("newTestTravelInfo", "newTestTravelInfo description")
        val travelInfo = createTravelInfo()
        val image = MockMultipartFile("https://image/newTravelInfo", null)

        given(travelInfoRepository.findById(any<UUID>())).willReturn(travelInfo)
        given(updateImageUsecase.execute(any<String>(), any<MultipartFile>()))
            .willReturn(image.name)

        updateTravelInfoUsecase.execute(travelInfo.id!!, image, request)

        then(travelInfoRepository).should().save(check {
            assertAll(
                { assertEquals(request.title, it.title) },
                { assertEquals(request.description, it.description) },
                { assertEquals(image.name, it.imageUrl) },
            )
        })
    }
}