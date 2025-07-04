package kr.motung_i.backend.domain.item.usecase.impl

import kr.motung_i.backend.domain.item.presentation.dto.request.RemoveItemRequest
import kr.motung_i.backend.domain.user.usecase.SuspensionUserUsecase
import kr.motung_i.backend.global.exception.CustomException
import kr.motung_i.backend.global.exception.enums.CustomErrorCode
import kr.motung_i.backend.persistence.item.repository.ItemRepository
import kr.motung_i.backend.persistence.review_report.entity.ReportReason
import kr.motung_i.backend.persistence.user.entity.User
import kr.motung_i.backend.persistence.user_suspension.entity.SuspensionPeriod
import kr.motung_i.backend.persistence.user_suspension.entity.SuspensionTarget
import kr.motung_i.backend.testfixture.ItemFixture.createTestItem
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.any
import org.mockito.kotlin.given
import org.mockito.kotlin.then
import java.util.*

@ExtendWith(MockitoExtension::class)
class RemoveItemUsecaseImplTest {
    @Mock
    private lateinit var itemRepository: ItemRepository

    @Mock
    private lateinit var suspensionUserUsecase: SuspensionUserUsecase

    @InjectMocks
    private lateinit var removeItemUsecaseImpl: RemoveItemUsecaseImpl

    @Test
    @DisplayName("꿀템을 정상 삭제하면 delete를 호출한다")
    fun 꿀템을_정상_삭제하면_delete를_호출한다() {
        val request = RemoveItemRequest(null)
        val item = createTestItem()

        given(itemRepository.findById(any<UUID>())).willReturn(item)

        removeItemUsecaseImpl.execute(item.id!!, request)

        then(itemRepository).should().delete(item)
    }

    @Test
    @DisplayName("꿀템을 정상 삭제하면 suspensionUserUsecase를 호출한다")
    fun 꿀템을_정상_삭제하면_suspensionUserUsecase를_호출한다() {
        val request = RemoveItemRequest(SuspensionPeriod.entries.random())
        val item = createTestItem()

        given(itemRepository.findById(any<UUID>())).willReturn(item)

        removeItemUsecaseImpl.execute(item.id!!, request)

        then(suspensionUserUsecase).should().execute(
            any<User>(),
            any<SuspensionTarget>(),
            any<Set<ReportReason>>(),
            any<SuspensionPeriod>()
        )
    }

    @Test
    @DisplayName("승인된 꿀템을 삭제하면 예외가 발생한다")
    fun 승인된_꿀템을_삭제하면_예외가_발생한다() {
        val request = RemoveItemRequest(null)
        val item = createTestItem(rankNumber = 1)

        given(itemRepository.findById(any<UUID>())).willReturn(item)

        val throws = assertThrows<CustomException> {
            removeItemUsecaseImpl.execute(item.id!!, request)
        }

        assertEquals(CustomErrorCode.CANNOT_MODIFY_APPROVED_ITEM, throws.customErrorCode)
    }

    @Test
    @DisplayName("존재하지 않는 꿀템을 요청하면 예외가 발생한다")
    fun 존재하지_않는_꿀템을_요청하면_예외가_발생한다() {
        val notExistItemId = UUID.randomUUID()
        val request = RemoveItemRequest(null)

        given(itemRepository.findById(notExistItemId)).willReturn(null)

        val throws = assertThrows<CustomException> {
            removeItemUsecaseImpl.execute(notExistItemId, request)
        }

        assertEquals(CustomErrorCode.NOT_FOUND_ITEM, throws.customErrorCode)
    }
}