package kr.motung_i.backend.persistence.tour.entity

import jakarta.persistence.*
import kr.motung_i.backend.persistence.BaseEntity
import kr.motung_i.backend.persistence.tour_location.entity.TourLocation
import kr.motung_i.backend.persistence.user.entity.User
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import org.hibernate.annotations.UuidGenerator
import java.util.*

@Entity
class Tour(
    @Id
    @UuidGenerator
    @Column(name = "TOUR_ID")
    val id: UUID? = null,

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "USER_ID")
    @OnDelete(action = OnDeleteAction.CASCADE)
    val user: User,

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "TOUR_LOCATION_ID")
    @OnDelete(action = OnDeleteAction.CASCADE)
    val tourLocation: TourLocation,

    @Column(nullable = false)
    val restaurantComment: String,

    @Column(nullable = false)
    val cafeComment: String,

    @Column(nullable = false)
    val tourComment: String,

    @Column(nullable = false)
    val cultureComment: String,
): BaseEntity()