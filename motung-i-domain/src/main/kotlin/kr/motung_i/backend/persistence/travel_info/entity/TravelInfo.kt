package kr.motung_i.backend.persistence.travel_info.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import kr.motung_i.backend.persistence.BaseEntity
import org.hibernate.annotations.UuidGenerator
import java.util.*

@Entity
class TravelInfo (
    @Id
    @UuidGenerator
    val id: UUID? = null,

    @Column(nullable = false)
    val imageUrl: String,

    @Column(nullable = false)
    val description: String,
) : BaseEntity()