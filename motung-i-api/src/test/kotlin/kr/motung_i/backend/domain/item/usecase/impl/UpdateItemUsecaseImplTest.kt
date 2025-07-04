package kr.motung_i.backend.domain.item.usecase.impl

import kr.motung_i.backend.domain.item.presentation.dto.request.UpdateItemRequest
import kr.motung_i.backend.global.exception.CustomException
import kr.motung_i.backend.global.exception.enums.CustomErrorCode
import kr.motung_i.backend.persistence.item.repository.ItemRepository
import kr.motung_i.backend.testfixture.ItemFixture.createTestItem
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.any
import org.mockito.kotlin.check
import org.mockito.kotlin.given
import org.mockito.kotlin.then
import java.util.*

@ExtendWith(MockitoExtension::class)
class UpdateItemUsecaseImplTest {
    @Mock
    private lateinit var itemRepository: ItemRepository

    @InjectMocks
    private lateinit var updateItemUsecaseImpl: UpdateItemUsecaseImpl

    @Test
    @DisplayName("정상적으로 요청을 처리하면 꿀템에 변경사항이 반영된다")
    fun 정상적으로_요청을_처리하면_꿀템에_변경사항이_반영된다() {
        val request = UpdateItemRequest(
            itemName = "newTestItem", coupangUrl = "https://coupang.com/new", description = "newTestItem description"
        )
        val item = createTestItem()

        given(itemRepository.findById(any<UUID>())).willReturn(item)

        updateItemUsecaseImpl.execute(item.id!!, request)

        then(itemRepository).should().save(check {
            assertAll(
                { assertEquals(request.itemName, it.name) },
                { assertEquals(request.coupangUrl, it.coupangUrl) },
                { assertEquals(request.description, it.description) }
            )
        })
    }

    @Test
    @DisplayName("저장되지 않는 꿀템을 요청하면 예외가 발생한다")
    fun 저장되지_않는_꿀템을_요청하면_예외가_발생한다() {
        val notExistItemId = UUID.randomUUID()
        val request = UpdateItemRequest(
            itemName = "newTestItem", coupangUrl = "https://coupang.com/new", description = "newTestItem description"
        )

        given(itemRepository.findById(notExistItemId)).willReturn(null)

        val throws = assertThrows<CustomException> {
            updateItemUsecaseImpl.execute(notExistItemId, request)
        }

        assertEquals(CustomErrorCode.NOT_FOUND_ITEM, throws.customErrorCode)
    }
}