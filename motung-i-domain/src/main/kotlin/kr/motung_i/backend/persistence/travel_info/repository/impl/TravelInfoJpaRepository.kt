package kr.motung_i.backend.persistence.travel_info.repository.impl

import kr.motung_i.backend.persistence.travel_info.entity.TravelInfo
import org.springframework.data.jpa.repository.JpaRepository

interface TravelInfoJpaRepository: JpaRepository<TravelInfo, Long> {
}