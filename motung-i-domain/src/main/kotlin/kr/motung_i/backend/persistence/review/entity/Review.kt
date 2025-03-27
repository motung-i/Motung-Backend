package kr.motung_i.backend.persistence.review.entity

import jakarta.persistence.CollectionTable
import jakarta.persistence.Column
import jakarta.persistence.ElementCollection
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import kr.motung_i.backend.persistence.BaseEntity
import kr.motung_i.backend.persistence.user.entity.User
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import org.hibernate.annotations.UuidGenerator
import java.util.UUID

@Entity
class Review (
    @Id
    @UuidGenerator
    @Column(name = "REVIEW_ID")
    val id: UUID? = null,

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "USER_ID")
    @OnDelete(action = OnDeleteAction.CASCADE)
    val user: User,

    @Column(nullable = false)
    val local: String,

    @Column(nullable = false)
    val isSuggested: Boolean,

    @Column(nullable = false)
    val description: String,

    @ElementCollection
    @CollectionTable(name="REVIEW_IMAGE_URL", joinColumns = [JoinColumn(name = "REVIEW_ID")])
    @Column(name = "IMAGE_URL")
    val imageUrls: List<String>,
) : BaseEntity()