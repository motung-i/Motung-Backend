package kr.motung_i.backend.domain.notification.presentation

import kr.motung_i.backend.domain.notification.presentation.dto.request.SendNotificationRequest
import kr.motung_i.backend.domain.notification.usecase.SendNotificationUsecase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("admin/notification")
class AdminNotificationController(
    private val sendNotificationUsecase: SendNotificationUsecase,
) {
    @PostMapping
    fun sendNotification(
        @RequestBody request: SendNotificationRequest,
    ): ResponseEntity<Unit> =
        sendNotificationUsecase.execute(request).run {
            ResponseEntity.noContent().build()
        }
}
