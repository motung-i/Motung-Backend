package kr.motung_i.backend.persistence.user.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import kr.motung_i.backend.persistence.user.entity.enums.Providers
import kr.motung_i.backend.persistence.user.entity.enums.Roles
import org.hibernate.annotations.UuidGenerator
import org.springframework.data.annotation.CreatedBy
import java.time.LocalDateTime
import java.util.UUID

@Entity(name = "users")
data class User(
    @Id
    @UuidGenerator
    @GeneratedValue
    val id: UUID? = null,
    @Column
    val name: String,
    @Column
    @Enumerated(EnumType.STRING)
    val roles: Roles,
    @Column
    val email: String,
    @Column(name = "oauth_id")
    val oauthId: String,
    @Column
    @Enumerated(EnumType.STRING)
    val provider: Providers,
    @Column
    @CreatedBy
    val createdBy: LocalDateTime = LocalDateTime.now(),
)
