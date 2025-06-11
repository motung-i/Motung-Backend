package kr.motung_i.backend.persistence.tour.entity

import jakarta.persistence.*
import kr.motung_i.backend.persistence.BaseEntity
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
    @JoinColumn(name = "USER_ID", unique = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    val user: User,

    @Embedded
    val local: Local,

    @Embedded
    val location: Location,

): BaseEntity() {
    @Column(nullable = false)
    var isActive: Boolean = false
        protected set

    fun copy(local: Local?, location: Location?): Tour =
        Tour(
            id = this.id,
            user = this.user,
            local = local ?: this.local,
            location = location ?: this.location,
        )

    fun activate() {
        isActive = true
    }
}