package kr.motung_i.backend.persistence.device_token.repository.impl

import kr.motung_i.backend.persistence.device_token.entity.DeviceToken
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface DeviceTokenJpaRepository : JpaRepository<DeviceToken, UUID>
