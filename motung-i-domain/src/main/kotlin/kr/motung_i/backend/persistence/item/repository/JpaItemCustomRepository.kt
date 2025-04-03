package kr.motung_i.backend.persistence.item.repository

import kr.motung_i.backend.persistence.item.entity.Item
import kr.motung_i.backend.persistence.item.entity.enums.ItemStatus
import org.springframework.stereotype.Repository

@Repository
class JpaItemCustomRepository(
    val itemRepository: ItemRepository,
) : ItemCustomRepository {
    override fun save(item: Item) {
        itemRepository.save(item)
    }

    override fun findByItemStatusOrderByRankNumber(itemStatus: ItemStatus): List<Item> =
        itemRepository.findByItemStatusOrderByRankNumber(itemStatus)
}