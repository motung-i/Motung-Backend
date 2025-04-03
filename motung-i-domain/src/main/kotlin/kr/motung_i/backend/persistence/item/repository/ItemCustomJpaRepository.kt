package kr.motung_i.backend.persistence.item.repository

import kr.motung_i.backend.persistence.item.entity.Item
import kr.motung_i.backend.persistence.item.entity.enums.ItemStatus
import org.springframework.stereotype.Repository
import java.util.UUID
import kotlin.jvm.optionals.getOrNull

@Repository
class ItemCustomJpaRepository(
    val itemRepository: ItemRepository,
) : ItemCustomRepository {
    override fun findById(id: UUID): Item? =
        itemRepository.findById(id).getOrNull()

    override fun save(item: Item) {
        itemRepository.save(item)
    }

    override fun findByItemStatusOrderByRankNumber(itemStatus: ItemStatus): List<Item> =
        itemRepository.findByItemStatusOrderByRankNumber(itemStatus)
}