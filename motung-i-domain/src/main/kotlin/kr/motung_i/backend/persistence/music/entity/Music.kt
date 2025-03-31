package kr.motung_i.backend.persistence.music.entity

import jakarta.persistence.*
import jakarta.validation.constraints.Size
import kr.motung_i.backend.persistence.BaseEntity
import kr.motung_i.backend.persistence.music.entity.enums.MusicStatus
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

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    var musicStatus: MusicStatus = MusicStatus.PENDING,

    @Size(max = 1, min = 5)
    var rankNumber: Int? = null,
) : BaseEntity() {
    fun update(title: String?, singer: String?, description: String?): Music =
        Music(
            id = this.id,
            user = this.user,
            title = title?.takeIf { it.isNotBlank() } ?: this.title,
            singer = singer?.takeIf { it.isNotBlank() } ?: this.singer,
            description = description?. takeIf { it.isNotBlank() } ?: this.description,
            musicStatus = this.musicStatus,
            rankNumber = rankNumber,
        )

    fun approveMusic(rankNumber: Int) {
        musicStatus = MusicStatus.APPROVED
        this.rankNumber = rankNumber
    }

    fun cancelMusic(rankNumber: Int) {
        musicStatus = MusicStatus.PENDING
        this.rankNumber = null
    }
}