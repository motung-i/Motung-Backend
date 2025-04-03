package kr.motung_i.backend.domain.item.presentation

import jakarta.validation.Valid
import kr.motung_i.backend.domain.item.presentation.dto.request.CreateItemRequest
import kr.motung_i.backend.domain.item.presentation.dto.response.ItemsResponse
import kr.motung_i.backend.domain.item.usecase.CreateItemUsecase
import kr.motung_i.backend.domain.item.usecase.FetchApprovedItemUsecase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("item")
class ItemController(
    private val createItemUsecase: CreateItemUsecase,
    private val fetchApprovedItemUsecase: FetchApprovedItemUsecase,
) {
    @PostMapping
    fun createItem(@Valid @RequestBody createItemRequest: CreateItemRequest): ResponseEntity<Unit> =
        createItemUsecase.execute(createItemRequest).run {
            ResponseEntity.noContent().build()
        }

    @GetMapping
    fun fetchApprovedItems(): ResponseEntity<ItemsResponse> =
        fetchApprovedItemUsecase.execute().run {
            ResponseEntity.ok(this)
        }
}