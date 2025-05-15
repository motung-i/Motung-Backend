package kr.motung_i.backend.persistence.user.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
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
    @Column(name = "USER_ID")
    val id: UUID? = null,

    @Column(nullable = false)
    val email: String,

    @Column(nullable = false, name = "OAUTH_ID")
    val oauthId: String,

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    val provider: Provider,
) : BaseEntity() {
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    var role: Role = Role.ROLE_PENDING
        protected set

    @Column(unique = true)
    var nickname: String? = null
        protected set

    fun approve() {
        role = Role.ROLE_USER
    }

    fun updateNickname(newNickname: String) {
        nickname = newNickname
    }
}
