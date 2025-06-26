package kr.motung_i.backend.domain.item.usecase.impl

import kr.motung_i.backend.domain.item.presentation.dto.request.UpdateItemRequest
import kr.motung_i.backend.domain.item.usecase.UpdateItemUsecase
import kr.motung_i.backend.global.exception.CustomException
import kr.motung_i.backend.global.exception.enums.CustomErrorCode
import kr.motung_i.backend.persistence.item.repository.ItemRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
@Transactional
class UpdateItemUsecaseImpl(
    private val itemRepository: ItemRepository,
): UpdateItemUsecase {
    override fun execute(itemId: UUID, request: UpdateItemRequest) {
        val savedItem = itemRepository.findById(itemId)
            ?: throw CustomException(CustomErrorCode.NOT_FOUND_ITEM)
        itemRepository.save(
            savedItem.copy(
                name = request.itemName,
                coupangUrl = request.coupangUrl,
                description = request.description,
            )
        )
    }
}