package kr.motung_i.backend.persistence.item.entity

import jakarta.persistence.*
import kr.motung_i.backend.persistence.BaseEntity
import kr.motung_i.backend.persistence.item.entity.enums.ItemStatus
import kr.motung_i.backend.persistence.user.entity.User
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import org.hibernate.annotations.UuidGenerator
import java.util.UUID

@Entity
class Item (
    @Id
    @UuidGenerator
    @Column(name = "ITEM_ID")
    val id: UUID? = null,

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "USER_ID")
    @OnDelete(action = OnDeleteAction.CASCADE)
    val user: User,

    @Column(nullable = false)
    val name: String,

    @Column(nullable = false)
    val description: String,

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    var itemStatus: ItemStatus = ItemStatus.PENDING,

    @Column(unique = true)
    var rankNumber: Int? = null,
) : BaseEntity() {
    fun update(name: String?, description: String?): Item =
        Item(
            id = this.id,
            user = this.user,
            name = name?.takeIf { it.isNotBlank() } ?: this.name,
            description = description?. takeIf { it.isNotBlank() } ?: this.description,
            itemStatus = this.itemStatus,
            rankNumber = rankNumber,
        )

    fun approveItem(rankNumber: Int) {
        itemStatus = ItemStatus.APPROVED
        this.rankNumber = rankNumber
    }

    fun cancelItem() {
        itemStatus = ItemStatus.PENDING
        this.rankNumber = null
    }
}