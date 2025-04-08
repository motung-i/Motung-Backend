package kr.motung_i.backend.persistence.travel_info.repository.impl

import kr.motung_i.backend.persistence.travel_info.entity.TravelInfo
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface TravelInfoJpaRepository: JpaRepository<TravelInfo, UUID> {
}