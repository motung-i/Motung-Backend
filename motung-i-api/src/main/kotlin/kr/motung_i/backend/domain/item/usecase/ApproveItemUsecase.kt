package kr.motung_i.backend.domain.item.usecase

import kr.motung_i.backend.domain.item.presentation.dto.request.ApproveItemRequest
import kr.motung_i.backend.global.exception.CustomException
import kr.motung_i.backend.global.exception.enums.CustomErrorCode
import kr.motung_i.backend.persistence.item.repository.ItemCustomRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
@Transactional
class ApproveItemUsecase(
    private val itemCustomRepository: ItemCustomRepository,
) {
    fun execute(itemId: UUID, approveItemRequest: ApproveItemRequest) {
        val rankItem = itemCustomRepository.findByRankNumber(approveItemRequest.rankNumber)
        val requestItem = itemCustomRepository.findById(itemId)
            ?: throw CustomException(CustomErrorCode.NOT_FOUND_ITEM)

        if (rankItem == requestItem) {
            throw CustomException(CustomErrorCode.ITEM_ALREADY_APPROVED)
        }

        rankItem?.cancelItem()
        requestItem.approveItem(approveItemRequest.rankNumber)
    }
}