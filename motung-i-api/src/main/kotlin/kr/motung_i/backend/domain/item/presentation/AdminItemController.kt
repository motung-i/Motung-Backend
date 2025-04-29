package kr.motung_i.backend.domain.item.presentation

import jakarta.validation.Valid
import kr.motung_i.backend.domain.item.presentation.dto.request.ApproveItemRequest
import kr.motung_i.backend.domain.item.presentation.dto.request.RemoveItemRequest
import kr.motung_i.backend.domain.item.presentation.dto.request.UpdateItemRequest
import kr.motung_i.backend.domain.item.presentation.dto.response.ItemsResponse
import kr.motung_i.backend.domain.item.usecase.ApproveItemUsecase
import kr.motung_i.backend.domain.item.usecase.FetchPendingItemUsecase
import kr.motung_i.backend.domain.item.usecase.RemoveItemUsecase
import kr.motung_i.backend.domain.item.usecase.UpdateItemUsecase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.UUID

@RestController
@RequestMapping("/admin/item")
class AdminItemController(
    private val fetchPendingItemUsecase: FetchPendingItemUsecase,
    private val updateItemUsecase: UpdateItemUsecase,
    private val approveItemUsecase: ApproveItemUsecase,
    private val removeItemUsecase: RemoveItemUsecase,
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

    @PostMapping("{itemId}")
    fun approveItem(
        @PathVariable itemId: UUID,
        @Valid @RequestBody approveItemRequest: ApproveItemRequest,
    ): ResponseEntity<Unit> =
        approveItemUsecase.execute(itemId, approveItemRequest).run {
            ResponseEntity.noContent().build()
        }

    @DeleteMapping("{itemId}")
    fun removeItem(
        @PathVariable itemId: UUID,
        @RequestBody request: RemoveItemRequest,
    ): ResponseEntity<Unit> =
        removeItemUsecase.execute(itemId, request).run {
            ResponseEntity.noContent().build()
        }
}