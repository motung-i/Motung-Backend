package kr.motung_i.backend.domain.notification.usecase

import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.MulticastMessage
import com.google.firebase.messaging.Notification
import kr.motung_i.backend.domain.notification.presentation.dto.request.SendNotificationRequest
import kr.motung_i.backend.global.exception.CustomException
import kr.motung_i.backend.global.exception.enums.CustomErrorCode
import kr.motung_i.backend.persistence.device_token.DeviceTokenRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

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
                .setNotification(
                    Notification
                        .builder()
                        .setTitle(request.title)
                        .setBody(request.body)
                        .build(),
                ).build()

        val result = FirebaseMessaging.getInstance().sendEachForMulticast(message)
        val logger: Logger = LoggerFactory.getLogger(SendNotificationUsecase::class.java)
        logger.info("FCM multicast sent: success=${result.successCount}, failure=${result.failureCount}")
    }
}
