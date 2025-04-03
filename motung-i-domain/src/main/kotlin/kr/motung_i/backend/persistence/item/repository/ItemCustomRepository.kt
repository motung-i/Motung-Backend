package kr.motung_i.backend.persistence.item.repository

import kr.motung_i.backend.persistence.item.entity.Item
import kr.motung_i.backend.persistence.item.entity.enums.ItemStatus
import java.util.UUID

interface ItemCustomRepository {
    fun findById(id: UUID): Item?
    fun save(item : Item)
    fun delete(item: Item)
    fun findByItemStatusOrderByRankNumber(itemStatus: ItemStatus): List<Item>
    fun findByRankNumber(rankNumber: Int): Item?
}