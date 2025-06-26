package kr.motung_i.backend.domain.item.usecase

import kr.motung_i.backend.domain.item.presentation.dto.request.ApproveItemRequest
import java.util.UUID

interface ApproveItemUsecase {
    fun execute(itemId: UUID, request: ApproveItemRequest)
}