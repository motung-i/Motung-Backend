package kr.motung_i.backend.persistence.device_token

import kr.motung_i.backend.persistence.device_token.entity.DeviceToken
import java.util.*

interface DeviceTokenRepository {
    fun save(deviceToken: DeviceToken): DeviceToken

    fun findAll(): List<DeviceToken>

    fun findByUserId(userId: UUID): Optional<DeviceToken>

    fun delete(deviceToken: DeviceToken)
}
