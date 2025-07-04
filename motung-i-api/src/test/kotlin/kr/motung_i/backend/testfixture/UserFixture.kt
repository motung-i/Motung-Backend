package kr.motung_i.backend.testfixture

import kr.motung_i.backend.persistence.review_report.entity.ReportReason
import kr.motung_i.backend.persistence.user.entity.User
import kr.motung_i.backend.persistence.user.entity.enums.Provider
import kr.motung_i.backend.persistence.user_suspension.entity.SuspensionPeriod
import kr.motung_i.backend.persistence.user_suspension.entity.SuspensionTarget
import kr.motung_i.backend.persistence.user_suspension.entity.UserSuspension
import java.time.LocalDateTime
import java.util.UUID
import kotlin.random.Random

object UserFixture {
    fun createTestUser(
        id: UUID = UUID.randomUUID(),
        email: String = "test@example.com",
        oauthId: String = "oauthId",
        provider: Provider = Provider.entries.random(),
        nickName: String? = null,
        isAdmin: Boolean = false,
    ): User {
        val user = User(id, email, oauthId, provider)
        nickName?.let { user.updateNickname(it) }
        if (isAdmin) {
            user.setAdmin()
        } else user.approve()

        return user
    }

    fun createTestUserSuspension(
        id: Long = Random.nextLong(),
        user: User = createTestUser(),
        target: SuspensionTarget = SuspensionTarget.entries.random(),
        reasons: Set<ReportReason> = setOf(ReportReason.entries.first()),
        suspensionPeriod: SuspensionPeriod = SuspensionPeriod.entries.random(),
        resumeAt: LocalDateTime = LocalDateTime.now().plusDays(suspensionPeriod.totalDays.toLong()),
        suspendedBy: User = createTestUser(),
    ): UserSuspension {
        return UserSuspension(
            id = id,
            user = user,
            target = target,
            reasons = reasons,
            suspensionPeriod = suspensionPeriod,
            resumeAt = resumeAt,
            suspendedBy = suspendedBy,
        )
    }
}