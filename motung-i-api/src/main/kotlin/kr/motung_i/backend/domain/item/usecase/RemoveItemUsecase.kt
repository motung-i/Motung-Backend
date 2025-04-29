package kr.motung_i.backend.domain.item.usecase

import kr.motung_i.backend.domain.item.presentation.dto.request.RemoveItemRequest
import kr.motung_i.backend.domain.user.usecase.SuspensionUserUsecase
import kr.motung_i.backend.global.exception.CustomException
import kr.motung_i.backend.global.exception.enums.CustomErrorCode
import kr.motung_i.backend.persistence.item.entity.enums.ItemStatus
import kr.motung_i.backend.persistence.item.repository.ItemRepository
import kr.motung_i.backend.persistence.user_suspension.entity.SuspensionTarget
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
@Transactional
class RemoveItemUsecase(
    private val itemRepository: ItemRepository,
    private val suspensionUserUsecase: SuspensionUserUsecase,
) {
    fun execute(itemId: UUID, request: RemoveItemRequest) {
        val requestItem = itemRepository.findById(itemId)
            ?: throw CustomException(CustomErrorCode.NOT_FOUND_ITEM)

        if (requestItem.itemStatus == ItemStatus.APPROVED) {
            throw CustomException(CustomErrorCode.CANNOT_MODIFY_APPROVED_ITEM)
        }

        suspensionUserUsecase.execute(
            user = requestItem.user,
            target = SuspensionTarget.ITEM,
            reasons = setOf(),
            suspensionPeriod = request.suspensionPeriod
        )

        itemRepository.delete(requestItem)
    }
}