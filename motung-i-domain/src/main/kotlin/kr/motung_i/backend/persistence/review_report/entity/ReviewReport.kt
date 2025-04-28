package kr.motung_i.backend.persistence.review_report.entity

import jakarta.persistence.*
import kr.motung_i.backend.persistence.review.entity.Review
import kr.motung_i.backend.persistence.user.entity.User
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction

@Entity
@Table(uniqueConstraints = [UniqueConstraint(columnNames = ["REVIEW_ID", "PROPOSER_ID"])])
class ReviewReport protected constructor(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "REVIEW_REPORT_ID")
    var id: Long? = null,

    @ElementCollection
    @CollectionTable(name = "REVIEW_REPORT_REASON", joinColumns = [JoinColumn(name = "REVIEW_REPORT_ID")])
    @Column(name = "REASONS", nullable = false)
    @Enumerated(EnumType.STRING)
    private var _reasons: MutableSet<ReportReason> = mutableSetOf(),

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "REVIEW_ID")
    @OnDelete(action = OnDeleteAction.CASCADE)
    var review: Review,

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "PROPOSER_ID")
    @OnDelete(action = OnDeleteAction.CASCADE)
    val proposer: User,
) {
    companion object {
        fun of(
            reasons: Collection<ReportReason>,
            review: Review,
            proposer: User
        ): ReviewReport = ReviewReport(
            _reasons = reasons.toMutableSet(),
            review = review,
            proposer = proposer
        )
    }

    val reasons: Set<ReportReason>
        get() = _reasons

    fun addReasons(newReasons: Set<ReportReason>) {
        _reasons = (_reasons + newReasons).toMutableSet()
    }
}