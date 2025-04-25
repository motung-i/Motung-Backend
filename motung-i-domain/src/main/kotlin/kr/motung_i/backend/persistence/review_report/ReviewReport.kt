package kr.motung_i.backend.persistence.review_report

import jakarta.persistence.*
import kr.motung_i.backend.persistence.review.entity.Review
import kr.motung_i.backend.persistence.user.entity.User
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction

@Entity
class ReviewReport(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "REVIEW_REPORT_ID")
    var id: Long? = null,

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    val reason: ReportReason,

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "REVIEW_ID")
    @OnDelete(action = OnDeleteAction.CASCADE)
    val review: Review,

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "PROPOSER_ID")
    @OnDelete(action = OnDeleteAction.CASCADE)
    val proposer: User,
)