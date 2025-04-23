package kr.motung_i.backend.persistence.tour.entity

import jakarta.persistence.*
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

    @Embedded
    @AttributeOverrides(
        AttributeOverride(name = "lat", column = Column(name = "start_lat")),
        AttributeOverride(name = "lon", column = Column(name = "start_lon")),
    )
    val startLocation: Location,

    @Embedded
    @AttributeOverrides(
        AttributeOverride(name = "lat", column = Column(name = "goal_lat")),
        AttributeOverride(name = "lon", column = Column(name = "goal_lon")),
    )
    val goalLocation: Location,

    @Column(nullable = false)
    val goalLocal: String,

    @Column(nullable = false)
    val aiRestaurantComment: String,

    @Column(nullable = false)
    val aiCafeComment: String,

    @Column(nullable = false)
    val aiTourComment: String,

    @Column(nullable = false)
    val aiCultureComment: String,
)