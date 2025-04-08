package kr.motung_i.backend.persistence.travel_info.repository

import kr.motung_i.backend.persistence.travel_info.entity.TravelInfo

interface TravelInfoRepository {
    fun findAll(): List<TravelInfo>
}