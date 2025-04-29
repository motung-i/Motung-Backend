package kr.motung_i.backend.domain.music.presentation.dto.request

import kr.motung_i.backend.persistence.user_suspension.entity.SuspensionPeriod

data class RemoveMusicRequest(
    val suspensionPeriod: SuspensionPeriod?,
)