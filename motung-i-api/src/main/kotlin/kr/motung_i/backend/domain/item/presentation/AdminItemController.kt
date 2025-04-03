package kr.motung_i.backend.domain.item.presentation

import jakarta.validation.Valid
import kr.motung_i.backend.domain.item.presentation.dto.request.UpdateItemRequest
import kr.motung_i.backend.domain.item.presentation.dto.response.ItemsResponse
import kr.motung_i.backend.domain.item.usecase.FetchPendingItemUsecase
import kr.motung_i.backend.domain.item.usecase.UpdateItemUsecase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/admin/item")
class AdminItemController(
    private val fetchPendingItemUsecase: FetchPendingItemUsecase,
    private val updateItemUsecase: UpdateItemUsecase,
) {
    @GetMapping
    fun fetchPendingItems(): ResponseEntity<ItemsResponse> =
        fetchPendingItemUsecase.execute().run {
            ResponseEntity.ok(this)
        }

    @PatchMapping("{itemId}")
    fun updateItem(
        @PathVariable itemId: UUID,
        @RequestBody updateItemRequest: UpdateItemRequest,
    ): ResponseEntity<Unit> =
        updateItemUsecase.execute(itemId, updateItemRequest).run {
            ResponseEntity.noContent().build()
        }
}