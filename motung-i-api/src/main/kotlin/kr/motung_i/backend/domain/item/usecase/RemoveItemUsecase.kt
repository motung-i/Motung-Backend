package kr.motung_i.backend.domain.item.usecase

import kr.motung_i.backend.global.exception.CustomException
import kr.motung_i.backend.global.exception.enums.CustomErrorCode
import kr.motung_i.backend.persistence.item.entity.enums.ItemStatus
import kr.motung_i.backend.persistence.item.repository.ItemRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
@Transactional
class RemoveItemUsecase(
    private val itemRepository: ItemRepository,
) {
    fun execute(itemId: UUID) {
        val requestItem = itemRepository.findById(itemId)
            ?: throw CustomException(CustomErrorCode.NOT_FOUND_ITEM)

        if (requestItem.itemStatus == ItemStatus.APPROVED) {
            throw CustomException(CustomErrorCode.CANNOT_MODIFY_APPROVED_ITEM)
        }

        itemRepository.delete(requestItem)
    }
}