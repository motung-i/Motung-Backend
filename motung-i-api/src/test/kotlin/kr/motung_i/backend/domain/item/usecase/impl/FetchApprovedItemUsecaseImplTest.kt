package kr.motung_i.backend.domain.item.usecase.impl

import kr.motung_i.backend.persistence.item.entity.enums.ItemStatus
import kr.motung_i.backend.persistence.item.repository.ItemRepository
import kr.motung_i.backend.testfixture.ItemFixture.createTestItem
import org.junit.jupiter.api.Assertions.assertAll
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.any
import org.mockito.kotlin.given

@ExtendWith(MockitoExtension::class)
class FetchApprovedItemUsecaseImplTest {
    @Mock
    private lateinit var itemRepository: ItemRepository

    @InjectMocks
    private lateinit var fetchApprovedItemUsecaseImpl: FetchApprovedItemUsecaseImpl

    @Test
    @DisplayName("꿀템을 조회하면 올바른 DTO 형식으로 반환한다")
    fun 꿀템을_조회하면_올바른_DTO_형식으로_반환한다() {
        val items = listOf(createTestItem(), createTestItem())
        given(itemRepository.findByItemStatusOrderByRankNumber(any<ItemStatus>()))
            .willReturn(items)

        val result = fetchApprovedItemUsecaseImpl.execute()

        items.zip(result.items).forEach { (item, dto) ->
            assertAll(
                { assertEquals(item.name, dto.name) },
                { assertEquals(item.description, dto.description) },
                { assertEquals(item.coupangUrl, dto.coupangUrl) }
            )
        }
    }
}