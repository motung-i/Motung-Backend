package kr.motung_i.backend.persistence.device_token.repository.impl

import kr.motung_i.backend.persistence.device_token.DeviceTokenRepository
import kr.motung_i.backend.persistence.device_token.entity.DeviceToken
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class DeviceTokenRepositoryImpl(
    private val deviceTokenJpaRepository: DeviceTokenJpaRepository,
) : DeviceTokenRepository {
    override fun save(deviceToken: DeviceToken): DeviceToken = deviceTokenJpaRepository.save(deviceToken)

    override fun findAll(): List<DeviceToken> = deviceTokenJpaRepository.findAll()

    override fun findByUserId(userId: UUID): Optional<DeviceToken> = deviceTokenJpaRepository.findById(userId)

    override fun delete(deviceToken: DeviceToken) = deviceTokenJpaRepository.delete(deviceToken)
}
