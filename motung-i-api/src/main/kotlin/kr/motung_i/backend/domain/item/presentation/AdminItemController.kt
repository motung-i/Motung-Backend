package kr.motung_i.backend.domain.item.presentation

import kr.motung_i.backend.domain.item.presentation.dto.response.ItemsResponse
import kr.motung_i.backend.domain.item.usecase.FetchPendingItemUsecase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/admin/item")
class AdminItemController(
    private val fetchPendingItemUsecase: FetchPendingItemUsecase,
) {
    @GetMapping
    fun fetchPendingItems(): ResponseEntity<ItemsResponse> =
        fetchPendingItemUsecase.execute().run {
            ResponseEntity.ok(this)
        }
}