package kr.motung_i.backend.persistence.review.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*
import kr.motung_i.backend.persistence.BaseEntity
import kr.motung_i.backend.persistence.review_report.entity.ReviewReport
import kr.motung_i.backend.persistence.tour.entity.Local
import kr.motung_i.backend.persistence.user.entity.User
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import org.hibernate.annotations.UuidGenerator
import java.util.UUID

@Entity
@Table(
    name = "REVIEW",
    uniqueConstraints = [UniqueConstraint(columnNames = ["USER_ID", "LOCAL_ALIAS", "DESCRIPTION"])]
)
class Review (
    @Id
    @UuidGenerator
    @Column(name = "REVIEW_ID")
    val id: UUID? = null,

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "USER_ID")
    @OnDelete(action = OnDeleteAction.CASCADE)
    val user: User,

    @Embedded
    val local: Local,

    @Column(nullable = false)
    val isRecommend: Boolean,

    @Column(nullable = false)
    val description: String,

    @ElementCollection
    @CollectionTable(name="REVIEW_IMAGE_URL", joinColumns = [JoinColumn(name = "REVIEW_ID")])
    @Column(name = "IMAGE_URL")
    val imageUrls: List<String>,

    @OneToMany(mappedBy = "review", cascade = [CascadeType.ALL], orphanRemoval = true)
    @JsonIgnore
    val reports: MutableList<ReviewReport> = mutableListOf()
) : BaseEntity()