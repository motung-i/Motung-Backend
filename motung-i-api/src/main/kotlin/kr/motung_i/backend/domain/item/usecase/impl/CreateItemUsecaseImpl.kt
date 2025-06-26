package kr.motung_i.backend.domain.item.usecase.impl

import kr.motung_i.backend.domain.item.presentation.dto.request.CreateItemRequest
import kr.motung_i.backend.domain.item.usecase.CreateItemUsecase
import kr.motung_i.backend.domain.user.usecase.FetchCurrentUserUsecase
import kr.motung_i.backend.persistence.item.entity.Item
import kr.motung_i.backend.persistence.item.repository.ItemRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class CreateItemUsecaseImpl(
    private val itemRepository: ItemRepository,
    private val fetchCurrentUserUsecase: FetchCurrentUserUsecase,
): CreateItemUsecase {
    override fun execute(request: CreateItemRequest) {
        itemRepository.save(
            Item(
                user = fetchCurrentUserUsecase.execute(),
                name = request.itemName,
                coupangUrl = request.coupangUrl,
                description = request.description,
            )
        )
    }
}