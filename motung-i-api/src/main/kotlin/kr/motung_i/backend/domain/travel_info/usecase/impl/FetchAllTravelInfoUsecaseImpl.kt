package kr.motung_i.backend.domain.travel_info.usecase.impl

import kr.motung_i.backend.domain.travel_info.presentation.dto.response.TravelInfoListResponse
import kr.motung_i.backend.domain.travel_info.usecase.FetchAllTravelInfoUsecase
import kr.motung_i.backend.persistence.travel_info.repository.TravelInfoRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class FetchAllTravelInfoUsecaseImpl(
    private val travelInfoRepository: TravelInfoRepository,
): FetchAllTravelInfoUsecase {
    override fun execute(): TravelInfoListResponse =
        TravelInfoListResponse.from(
            travelInfoRepository.findAll()
        )
}