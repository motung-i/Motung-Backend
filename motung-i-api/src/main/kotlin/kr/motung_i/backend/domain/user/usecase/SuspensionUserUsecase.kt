package kr.motung_i.backend.domain.user.usecase

import kr.motung_i.backend.global.exception.CustomException
import kr.motung_i.backend.global.exception.enums.CustomErrorCode
import kr.motung_i.backend.persistence.review_report.entity.ReportReason
import kr.motung_i.backend.persistence.user.entity.User
import kr.motung_i.backend.persistence.user.entity.enums.Role
import kr.motung_i.backend.persistence.user_suspension.entity.SuspensionPeriod
import kr.motung_i.backend.persistence.user_suspension.entity.SuspensionTarget
import kr.motung_i.backend.persistence.user_suspension.entity.UserSuspension
import kr.motung_i.backend.persistence.user_suspension.repository.UserSuspensionRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
@Transactional
class SuspensionUserUsecase(
    private val userSuspensionRepository: UserSuspensionRepository,
    private val fetchCurrentUserUsecase: FetchCurrentUserUsecase
) {
    fun execute(
        user: User,
        target: SuspensionTarget,
        reasons: Set<ReportReason>,
        suspensionPeriod: SuspensionPeriod?
    ) {
        if (suspensionPeriod == null) {
            return
        }

        if (user.role == Role.ROLE_ADMIN) {
            throw CustomException(CustomErrorCode.SUSPENDED_ADMIN)
        }

        val currentUser = fetchCurrentUserUsecase.execute()
        val savedUserSuspension = userSuspensionRepository.findWithReasonsByUser(user)

        if (savedUserSuspension == null) {
            userSuspensionRepository.save(
                UserSuspension(
                    user = user,
                    target = target,
                    reasons = reasons,
                    suspensionPeriod = suspensionPeriod,
                    resumeAt = LocalDateTime.now().plus(suspensionPeriod.period),
                    suspendedBy = currentUser
                )
            )
            return
        }

        if (savedUserSuspension.suspensionPeriod.totalDays >= suspensionPeriod.totalDays) {
            return
        }

        userSuspensionRepository.save(
            savedUserSuspension.copy(
                target = target,
                reasons = reasons,
                suspensionPeriod = suspensionPeriod,
                resumeAt = savedUserSuspension.createdAt!!.plus(suspensionPeriod.period),
                suspendedBy = currentUser
            )
        )
    }
}