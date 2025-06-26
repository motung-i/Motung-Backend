package kr.motung_i.backend.domain.item.usecase.impl

import kr.motung_i.backend.domain.item.presentation.dto.response.ItemsResponse
import kr.motung_i.backend.domain.item.usecase.FetchPendingItemUsecase
import kr.motung_i.backend.persistence.item.entity.enums.ItemStatus.*
import kr.motung_i.backend.persistence.item.repository.ItemRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class FetchPendingItemUsecaseImpl(
    private val itemRepository: ItemRepository,
): FetchPendingItemUsecase {
    override fun execute(): ItemsResponse =
        ItemsResponse.from(
            itemRepository.findByItemStatusOrderByRankNumber(PENDING)
        )
}