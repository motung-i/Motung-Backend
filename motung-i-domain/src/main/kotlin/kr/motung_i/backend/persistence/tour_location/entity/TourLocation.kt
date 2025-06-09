package kr.motung_i.backend.persistence.tour_location.entity

import jakarta.persistence.*
import kr.motung_i.backend.persistence.user.entity.User
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction

@Entity
class TourLocation(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TOUR_LOCATION_ID")
    val id: Long? = null,

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "USER_ID", unique = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    val user: User,

    @Embedded
    val local: Local,

    @Embedded
    val location: Location,
) {
    fun copy(
        user: User? = null,
        local: Local? = null,
        location: Location? = null,
    ): TourLocation =
        TourLocation(
            id = this.id,
            user = user ?: this.user,
            local = local ?: this.local.copy(),
            location = location ?: this.location.copy(),
        )
}