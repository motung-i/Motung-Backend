package kr.motung_i.backend.domain.travel_info.usecase

import kr.motung_i.backend.domain.travel_info.presentation.dto.response.TravelInfoListResponse
import kr.motung_i.backend.persistence.travel_info.repository.TravelInfoRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class FetchAllTravelInfoUsecase(
    private val travelInfoRepository: TravelInfoRepository,
) {
    fun execute(): TravelInfoListResponse =
        TravelInfoListResponse.from(
            travelInfoRepository.findAll()
        )
}