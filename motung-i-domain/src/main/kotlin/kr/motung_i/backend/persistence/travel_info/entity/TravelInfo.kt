package kr.motung_i.backend.persistence.travel_info.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import jakarta.persistence.UniqueConstraint
import kr.motung_i.backend.persistence.BaseEntity
import org.hibernate.annotations.UuidGenerator
import java.util.*

@Entity
@Table(
    name = "TRAVEL_INFO",
    uniqueConstraints = [UniqueConstraint(columnNames = ["TITLE", "DESCRIPTION"])]
)
class TravelInfo (
    @Id
    @UuidGenerator
    @Column(name = "TRAVEL_INFO_ID")
    val id: UUID? = null,

    @Column(nullable = false)
    val imageUrl: String,

    @Column(nullable = false)
    val title: String,

    @Column(nullable = false)
    val description: String,
) : BaseEntity() {
    fun update(imageUrl: String?, title: String?, description: String?): TravelInfo =
        TravelInfo(
            id = id,
            imageUrl = imageUrl?.takeIf { it.isNotBlank() } ?: this.imageUrl,
            title = title?.takeIf { it.isNotBlank() } ?: this.title,
            description = description?.takeIf { it.isNotBlank() } ?: this.description,
        )
}