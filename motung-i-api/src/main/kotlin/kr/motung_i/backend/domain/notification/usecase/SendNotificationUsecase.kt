package kr.motung_i.backend.domain.notification.usecase

import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.MulticastMessage
import com.google.firebase.messaging.Notification
import kr.motung_i.backend.domain.notification.presentation.dto.request.SendNotificationRequest
import kr.motung_i.backend.global.exception.CustomException
import kr.motung_i.backend.global.exception.enums.CustomErrorCode
import kr.motung_i.backend.persistence.device_token.DeviceTokenRepository
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class SendNotificationUsecase(
    private val deviceTokenRepository: DeviceTokenRepository,
) {
    fun execute(request: SendNotificationRequest) {
        val deviceTokens: MutableList<String> = mutableListOf()
        if (request.targetUserId == null) {
            val tokens = deviceTokenRepository.findAll().map { it.deviceToken }
            deviceTokens.addAll(tokens)
        } else {
            val token =
                deviceTokenRepository
                    .findByUserId(request.targetUserId)
                    .orElseThrow { throw CustomException(CustomErrorCode.NOT_FOUND_USER) }
                    .deviceToken
            deviceTokens.add(token)
        }

        val message: MulticastMessage =
            MulticastMessage
                .builder()
                .addAllTokens(deviceTokens)
                .putData(
                    "time",
                    LocalDateTime.now().toString(),
                ).setNotification(
                    Notification
                        .builder()
                        .setTitle(request.title)
                        .setBody(request.body)
                        .setImage(request.image)
                        .build(),
                ).build()

        FirebaseMessaging.getInstance().sendEachForMulticast(message)
    }
}
