package kr.motung_i.backend.domain.item.usecase

import kr.motung_i.backend.domain.item.presentation.dto.request.CreateItemRequest
import kr.motung_i.backend.domain.user.usecase.FetchCurrentUserUsecase
import kr.motung_i.backend.persistence.item.entity.Item
import kr.motung_i.backend.persistence.item.repository.ItemRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class CreateItemUsecase(
    private val itemRepository: ItemRepository,
    private val fetchCurrentUserUsecase: FetchCurrentUserUsecase,
) {
    fun execute(createItemRequest: CreateItemRequest) {
        itemRepository.save(
            Item(
                user = fetchCurrentUserUsecase.execute(),
                name = createItemRequest.itemName,
                description = createItemRequest.description,
            )
        )
    }
}