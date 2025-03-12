package kr.motung_i.backend.domain.music.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne
import kr.motung_i.backend.domain.user.entity.User
import org.hibernate.annotations.UuidGenerator
import java.util.UUID

@Entity
class Music(
    @Id
    @UuidGenerator
    val id: UUID? = null,

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    val user: User,

    @Column(nullable = false)
    val name: String,

    @Column(nullable = false)
    val singer: String,

    @Column(nullable = false)
    val description: String,
)