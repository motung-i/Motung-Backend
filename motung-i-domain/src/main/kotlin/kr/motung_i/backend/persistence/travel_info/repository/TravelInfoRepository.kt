package kr.motung_i.backend.persistence.travel_info.repository

import kr.motung_i.backend.persistence.travel_info.entity.TravelInfo
import java.util.UUID

interface TravelInfoRepository {
    fun findById(id: UUID): TravelInfo?
    fun findAll(): List<TravelInfo>
    fun save(travelInfo: TravelInfo)
}