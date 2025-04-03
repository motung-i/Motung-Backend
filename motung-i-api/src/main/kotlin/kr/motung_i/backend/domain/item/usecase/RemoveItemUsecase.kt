package kr.motung_i.backend.domain.item.usecase

import kr.motung_i.backend.global.exception.CustomException
import kr.motung_i.backend.global.exception.enums.CustomErrorCode
import kr.motung_i.backend.persistence.item.entity.enums.ItemStatus
import kr.motung_i.backend.persistence.item.repository.ItemCustomRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
@Transactional
class RemoveItemUsecase(
    private val itemCustomRepository: ItemCustomRepository,
) {
    fun execute(itemId: UUID) {
        val requestItem = itemCustomRepository.findById(itemId)
            ?: throw CustomException(CustomErrorCode.NOT_FOUND_ITEM)

        if (requestItem.itemStatus == ItemStatus.APPROVED) {
            throw CustomException(CustomErrorCode.CANNOT_MODIFY_APPROVED_ITEM)
        }

        itemCustomRepository.delete(requestItem)
    }
}