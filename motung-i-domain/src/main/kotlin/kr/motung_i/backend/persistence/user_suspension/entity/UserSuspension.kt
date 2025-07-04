package kr.motung_i.backend.persistence.user_suspension.entity

import jakarta.persistence.*
import kr.motung_i.backend.persistence.BaseEntity
import kr.motung_i.backend.persistence.review_report.entity.ReportReason
import kr.motung_i.backend.persistence.user.entity.User
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import java.time.LocalDateTime

@Entity
class UserSuspension(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_SUSPENSION_ID")
    val id: Long? = null,

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "USER_ID", unique = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    val user: User,

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    val target: SuspensionTarget,

    @ElementCollection
    @CollectionTable(name = "USER_SUSPENSION_REASON", joinColumns = [JoinColumn(name = "USER_SUSPENSION_ID")])
    @Column(name = "REASON", nullable = false)
    @Enumerated(EnumType.STRING)
    val reasons: Set<ReportReason> = setOf(),

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    val suspensionPeriod: SuspensionPeriod,

    @Column(nullable = false)
    val resumeAt: LocalDateTime,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SUSPENDED_BY_ID")
    @OnDelete(action = OnDeleteAction.SET_NULL)
    val suspendedBy: User?,
): BaseEntity() {
    fun copy(
        target: SuspensionTarget,
        reasons: Set<ReportReason>,
        suspensionPeriod: SuspensionPeriod,
        resumeAt: LocalDateTime,
        suspendedBy: User
    ): UserSuspension = UserSuspension(
        id = this.id,
        user = this.user,
        target = target,
        reasons = reasons,
        suspensionPeriod = suspensionPeriod,
        resumeAt = resumeAt,
        suspendedBy = suspendedBy,
    )
}