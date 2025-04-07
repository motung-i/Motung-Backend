package kr.motung_i.backend.persistence.item.repository.impl

import kr.motung_i.backend.persistence.item.entity.Item
import kr.motung_i.backend.persistence.item.entity.enums.ItemStatus
import kr.motung_i.backend.persistence.item.repository.ItemRepository
import org.springframework.stereotype.Repository
import java.util.UUID
import kotlin.jvm.optionals.getOrNull

@Repository
class ItemRepositoryImpl(
    val itemJpaRepository: ItemJpaRepository,
) : ItemRepository {
    override fun findById(id: UUID): Item? =
        itemJpaRepository.findById(id).getOrNull()

    override fun save(item: Item) {
        itemJpaRepository.save(item)
    }

    override fun delete(item: Item) {
        itemJpaRepository.delete(item)
    }

    override fun findByItemStatusOrderByRankNumber(itemStatus: ItemStatus): List<Item> =
        itemJpaRepository.findByItemStatusOrderByRankNumber(itemStatus)

    override fun findByRankNumber(rankNumber: Int): Item? =
        itemJpaRepository.findByRankNumber(rankNumber)
}