package kr.motung_i.backend.persistence.item.repository

import kr.motung_i.backend.persistence.item.entity.Item
import kr.motung_i.backend.persistence.item.entity.enums.ItemStatus

interface ItemCustomRepository {
    fun save(item : Item)
    fun findByItemStatusOrderByRankNumber(itemStatus: ItemStatus): List<Item>
}