package kr.motung_i.backend.domain.item.usecase

import kr.motung_i.backend.domain.item.presentation.dto.request.RemoveItemRequest
import java.util.UUID

interface RemoveItemUsecase {
    fun execute(itemId: UUID, request: RemoveItemRequest)
}