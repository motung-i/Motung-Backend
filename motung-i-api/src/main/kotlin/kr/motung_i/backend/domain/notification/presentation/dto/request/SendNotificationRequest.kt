package kr.motung_i.backend.domain.notification.presentation.dto.request

import java.util.UUID

data class SendNotificationRequest(
    val title: String,
    val body: String,
    val targetUserId: UUID?,
)
