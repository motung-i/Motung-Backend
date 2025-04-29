package kr.motung_i.backend.global.security.exception.dto.response

import kr.motung_i.backend.global.exception.enums.CustomErrorCode
import kr.motung_i.backend.persistence.user_suspension.entity.UserSuspension
import java.time.LocalDateTime

data class SuspensionUserExceptionResponse(
    val status: CustomErrorCode,
    val statusMessage: String,
    val suspensionPeriod: String,
    val resumeAt: LocalDateTime,
    val reasons: List<String>
) {
    companion object {
        fun toDto(userSuspension: UserSuspension): SuspensionUserExceptionResponse =
            SuspensionUserExceptionResponse(
                status = CustomErrorCode.SUSPENDED_USER,
                statusMessage = CustomErrorCode.SUSPENDED_USER.statusMessage,
                suspensionPeriod = userSuspension.suspensionPeriod.description,
                resumeAt = userSuspension.resumeAt,
                reasons = userSuspension.reasons.map { it.description },
            )
    }
}