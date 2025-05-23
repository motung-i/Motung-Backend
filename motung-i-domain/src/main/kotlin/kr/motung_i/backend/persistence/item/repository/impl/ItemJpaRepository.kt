package kr.motung_i.backend.persistence.item.repository.impl

import kr.motung_i.backend.persistence.item.entity.Item
import kr.motung_i.backend.persistence.item.entity.enums.ItemStatus
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface ItemJpaRepository : JpaRepository<Item, UUID> {
    fun findByItemStatusOrderByRankNumber(itemStatus: ItemStatus): List<Item>
    fun findByRankNumber(rank: Int): Item?
}