package kr.motung_i.backend.domain.user.entity

import jakarta.persistence.CollectionTable
import jakarta.persistence.Column
import jakarta.persistence.ElementCollection
import jakarta.persistence.Entity
import jakarta.persistence.Id
import org.hibernate.annotations.UuidGenerator
import java.util.UUID

@Entity
class User (
    @Id
    @UuidGenerator
    val id: UUID? = null,

    @Column(nullable = false)
    val name: String,

    @ElementCollection
    @CollectionTable(name = "roles")
    val role: List<Role>,
)