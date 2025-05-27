package kr.motung_i.backend.persistence.device_token.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import kr.motung_i.backend.persistence.BaseEntity
import java.util.UUID

@Entity
class DeviceToken(
    @Id
    val userId: UUID,
    @Column
    val deviceToken: String,
) : BaseEntity()
