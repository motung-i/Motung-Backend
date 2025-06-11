package kr.motung_i.backend.persistence.tour_comment.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToOne
import kr.motung_i.backend.persistence.BaseEntity
import kr.motung_i.backend.persistence.tour.entity.Tour
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction

@Entity
class TourComment(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TOUR_COMMENT_ID")
    val id: Long? = null,

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "TOUR_ID")
    @OnDelete(action = OnDeleteAction.CASCADE)
    val tour: Tour,

    @Column(nullable = false)
    val restaurantComment: String,

    @Column(nullable = false)
    val sightseeingSpotsComment: String,

    @Column(nullable = false)
    val cultureComment: String,
): BaseEntity()