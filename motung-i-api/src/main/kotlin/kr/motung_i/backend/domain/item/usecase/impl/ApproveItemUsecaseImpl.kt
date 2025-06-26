package kr.motung_i.backend.domain.item.usecase.impl

import kr.motung_i.backend.domain.item.presentation.dto.request.ApproveItemRequest
import kr.motung_i.backend.domain.item.usecase.ApproveItemUsecase
import kr.motung_i.backend.global.exception.CustomException
import kr.motung_i.backend.global.exception.enums.CustomErrorCode
import kr.motung_i.backend.persistence.item.repository.ItemRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
@Transactional
class ApproveItemUsecaseImpl(
    private val itemRepository: ItemRepository,
): ApproveItemUsecase {
    override fun execute(itemId: UUID, request: ApproveItemRequest) {
        val rankItem = itemRepository.findByRankNumber(request.rankNumber)
        val requestItem = itemRepository.findById(itemId)
            ?: throw CustomException(CustomErrorCode.NOT_FOUND_ITEM)

        if (rankItem == requestItem) {
            throw CustomException(CustomErrorCode.ITEM_ALREADY_APPROVED)
        }

        rankItem?.cancelItem()
        requestItem.approveItem(request.rankNumber)
    }
}