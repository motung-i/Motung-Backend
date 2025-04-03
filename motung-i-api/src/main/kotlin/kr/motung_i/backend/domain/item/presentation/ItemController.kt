package kr.motung_i.backend.domain.item.presentation

import kr.motung_i.backend.domain.item.presentation.dto.request.CreateItemRequest
import kr.motung_i.backend.domain.item.usecase.CreateItemUsecase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("item")
class ItemController(
    private val createItemUsecase: CreateItemUsecase,
) {
    @PostMapping
    fun createItem(@RequestBody createItemRequest: CreateItemRequest): ResponseEntity<Unit> =
        createItemUsecase.execute(createItemRequest).run {
            ResponseEntity.noContent().build()
        }
}