package kr.motung_i.backend.domain.user.usecase

import kr.motung_i.backend.persistence.review_report.entity.ReportReason
import kr.motung_i.backend.persistence.user.entity.User
import kr.motung_i.backend.persistence.user_suspension.entity.SuspensionPeriod
import kr.motung_i.backend.persistence.user_suspension.entity.SuspensionTarget

interface SuspensionUserUsecase {
    fun execute(
        user: User,
        target: SuspensionTarget,
        reasons: Set<ReportReason>,
        suspensionPeriod: SuspensionPeriod?
    )
}