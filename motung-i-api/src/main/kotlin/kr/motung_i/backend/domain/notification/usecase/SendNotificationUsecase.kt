package kr.motung_i.backend.domain.notification.usecase

import kr.motung_i.backend.domain.notification.presentation.dto.request.SendNotificationRequest

interface SendNotificationUsecase {
    fun execute(request: SendNotificationRequest)
}