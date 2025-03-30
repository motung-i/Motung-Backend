package kr.motung_i.backend.persistence.music.entity

import jakarta.persistence.*
import kr.motung_i.backend.persistence.BaseEntity
import kr.motung_i.backend.persistence.user.entity.User
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import org.hibernate.annotations.UuidGenerator
import java.util.UUID

@Entity
class Music(
    @Id
    @UuidGenerator
    @Column(name = "MUSIC_ID")
    val id: UUID? = null,

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "USER_ID")
    @OnDelete(action = OnDeleteAction.CASCADE)
    val user: User,

    @Column(nullable = false)
    val title: String,

    @Column(nullable = false)
    val singer: String,

    @Column(nullable = false)
    val description: String,
) : BaseEntity()