package kr.motung_i.backend.domain.item.usecase.impl

import kr.motung_i.backend.domain.item.presentation.dto.request.ApproveItemRequest
import kr.motung_i.backend.global.exception.CustomException
import kr.motung_i.backend.global.exception.enums.CustomErrorCode
import kr.motung_i.backend.persistence.item.entity.enums.ItemStatus
import kr.motung_i.backend.persistence.item.repository.ItemRepository
import kr.motung_i.backend.testfixture.ItemFixture.createTestItem
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.any
import org.mockito.kotlin.given
import java.util.UUID

@ExtendWith(MockitoExtension::class)
class ApproveItemUsecaseImplTest {
    @Mock
    private lateinit var itemRepository: ItemRepository

    @InjectMocks
    private lateinit var approveItemUsecaseImpl: ApproveItemUsecaseImpl

    @Test
    @DisplayName("꿀템을 승인하면 승인 상태로 변경한다")
    fun 꿀템을_승인하면_승인_상태로_변경한다() {
        val request = ApproveItemRequest(1)
        val item = createTestItem()

        given(itemRepository.findByRankNumber(any<Int>())).willReturn(null)
        given(itemRepository.findById(any<UUID>())).willReturn(item)

        approveItemUsecaseImpl.execute(item.id!!, request)

        assertEquals(ItemStatus.APPROVED, item.itemStatus)
    }

    @Test
    @DisplayName("정상적으로 요청을 처리하면 요청한 순위로 꿀템 순위를 변경한다")
    fun 정상적으로_요청을_처리하면_요청한_순위로_꿀템_순위를_변경한다() {
        val request = ApproveItemRequest(1)
        val item = createTestItem()

        given(itemRepository.findByRankNumber(any<Int>())).willReturn(null)
        given(itemRepository.findById(any<UUID>())).willReturn(item)

        approveItemUsecaseImpl.execute(item.id!!, request)

        assertEquals(request.rankNumber, item.rankNumber)
    }

    @Test
    @DisplayName("정상적으로 요청을 처리하면 요청한 순위에 있는 꿀템은 대기 상태로 변경한다")
    fun 정상적으로_요청을_처리하면_요청한_순위에_있는_꿀템은_대기_상태로_변경한다() {
        val request = ApproveItemRequest(1)
        val oldItem = createTestItem(rankNumber = request.rankNumber)
        val newItem = createTestItem()

        given(itemRepository.findByRankNumber(any<Int>())).willReturn(oldItem)
        given(itemRepository.findById(any<UUID>())).willReturn(newItem)

        approveItemUsecaseImpl.execute(newItem.id!!, request)

        assertEquals(ItemStatus.PENDING, oldItem.itemStatus)
    }

    @Test
    @DisplayName("정상적으로 요청을 처리하면 요청한 순위에 있는 아이템은 꿀템 순위에서 제거한다")
    fun 정상적으로_요청을_처리하면_요청한_순위에_있는_아이템은_꿀템_순위에서_제거한다() {
        val request = ApproveItemRequest(1)
        val oldItem = createTestItem(rankNumber = request.rankNumber)
        val newItem = createTestItem()

        given(itemRepository.findByRankNumber(any<Int>())).willReturn(oldItem)
        given(itemRepository.findById(any<UUID>())).willReturn(newItem)

        approveItemUsecaseImpl.execute(newItem.id!!, request)

        assertNull(oldItem.rankNumber)
    }

    @Test
    @DisplayName("요청한 꿀템이 이미 순위에 존재하면 예외가 발생한다")
    fun 요청한_꿀템이_이미_순위에_존재하면_예외가_발생한다() {
        val request = ApproveItemRequest(1)
        val item = createTestItem(rankNumber = request.rankNumber)

        given(itemRepository.findByRankNumber(any<Int>())).willReturn(item)
        given(itemRepository.findById(any<UUID>())).willReturn(item)

        val throws = assertThrows<CustomException> {
            approveItemUsecaseImpl.execute(item.id!!, request)
        }

        assertEquals(CustomErrorCode.ITEM_ALREADY_APPROVED, throws.customErrorCode)
    }
}