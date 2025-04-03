package kr.motung_i.backend.persistence.item.repository

import kr.motung_i.backend.persistence.item.entity.Item
import kr.motung_i.backend.persistence.item.entity.enums.ItemStatus
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface ItemRepository : JpaRepository<Item, UUID> {
    fun findByItemStatusOrderByRankNumber(itemStatus: ItemStatus): List<Item>
}