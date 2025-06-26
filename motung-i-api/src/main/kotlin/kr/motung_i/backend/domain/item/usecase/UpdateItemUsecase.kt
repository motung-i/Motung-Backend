package kr.motung_i.backend.domain.item.usecase

import kr.motung_i.backend.domain.item.presentation.dto.request.UpdateItemRequest
import java.util.UUID

interface UpdateItemUsecase {
    fun execute(itemId: UUID, request: UpdateItemRequest)
}