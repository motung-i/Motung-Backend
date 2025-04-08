package kr.motung_i.backend.persistence.travel_info.repository.impl

import kr.motung_i.backend.persistence.travel_info.entity.TravelInfo
import kr.motung_i.backend.persistence.travel_info.repository.TravelInfoRepository
import org.springframework.stereotype.Repository
import java.util.*
import kotlin.jvm.optionals.getOrNull

@Repository
class TravelInfoRepositoryImpl(
    private val travelInfoJpaRepository: TravelInfoJpaRepository,
): TravelInfoRepository {
    override fun findById(id: UUID): TravelInfo? =
        travelInfoJpaRepository.findById(id).getOrNull()

    override fun findAll(): List<TravelInfo> =
        travelInfoJpaRepository.findAll()

    override fun save(travelInfo: TravelInfo) {
        travelInfoJpaRepository.save(travelInfo)
    }
}