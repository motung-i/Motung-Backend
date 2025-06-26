package kr.motung_i.backend.domain.item.usecase

import kr.motung_i.backend.domain.item.presentation.dto.response.ItemsResponse

interface FetchApprovedItemUsecase {
    fun execute(): ItemsResponse
}