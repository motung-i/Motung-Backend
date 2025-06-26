package kr.motung_i.backend.domain.item.usecase

import kr.motung_i.backend.domain.item.presentation.dto.request.CreateItemRequest

interface CreateItemUsecase {
    fun execute(request: CreateItemRequest)
}