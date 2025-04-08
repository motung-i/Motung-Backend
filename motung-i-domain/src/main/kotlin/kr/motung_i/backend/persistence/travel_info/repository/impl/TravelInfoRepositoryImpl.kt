package kr.motung_i.backend.persistence.travel_info.repository.impl

import kr.motung_i.backend.persistence.travel_info.entity.TravelInfo
import kr.motung_i.backend.persistence.travel_info.repository.TravelInfoRepository
import org.springframework.stereotype.Repository

@Repository
class TravelInfoRepositoryImpl(
    private val travelInfoJpaRepository: TravelInfoJpaRepository,
): TravelInfoRepository {
    override fun findAll(): List<TravelInfo> =
        travelInfoJpaRepository.findAll()
}