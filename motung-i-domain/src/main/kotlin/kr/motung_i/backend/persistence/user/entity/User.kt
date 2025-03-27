package kr.motung_i.backend.persistence.user.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import kr.motung_i.backend.persistence.BaseEntity
import kr.motung_i.backend.persistence.user.entity.enums.Provider
import kr.motung_i.backend.persistence.user.entity.enums.Role
import org.hibernate.annotations.UuidGenerator
import java.util.UUID

@Entity
data class User(
    @Id
    @UuidGenerator
    @GeneratedValue
    val id: UUID? = null,

    @Column(nullable = false)
    val name: String,

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    val role: Role,

    @Column(nullable = false)
    val email: String,

    @Column(nullable = false, name = "oauth_id")
    val oauthId: String,

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    val provider: Provider,
) : BaseEntity()
