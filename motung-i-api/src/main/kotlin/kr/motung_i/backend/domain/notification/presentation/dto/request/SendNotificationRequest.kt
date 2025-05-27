package kr.motung_i.backend.domain.notification.presentation.dto.request

import java.util.UUID

data class SendNotificationRequest(
    val onProduction: Boolean,
    val title: String,
    val body: String,
    val image: String,
    val targetUserId: UUID?,
)
