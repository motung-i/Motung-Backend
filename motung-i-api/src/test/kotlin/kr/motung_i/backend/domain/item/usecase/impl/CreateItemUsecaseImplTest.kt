package kr.motung_i.backend.domain.item.usecase.impl

import kr.motung_i.backend.domain.item.presentation.dto.request.CreateItemRequest
import kr.motung_i.backend.domain.user.usecase.FetchCurrentUserUsecase
import kr.motung_i.backend.persistence.item.repository.ItemRepository
import kr.motung_i.backend.testfixture.UserFixture.createTestUser
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.check
import org.mockito.kotlin.given
import org.mockito.kotlin.then

@ExtendWith(MockitoExtension::class)
class CreateItemUsecaseImplTest {
    @Mock
    private lateinit var itemRepository: ItemRepository
    @Mock
    private lateinit var fetchCurrentUserUsecase: FetchCurrentUserUsecase

    @InjectMocks
    private lateinit var createItemUsecaseImpl: CreateItemUsecaseImpl

    @Test
    @DisplayName("꿀템을 저장하면 올바른 데이터로 저장한다")
    fun 꿀템을_저장하면_올바른_데이터로_저장한다() {
        val request = CreateItemRequest("testItem", "https://coupang.com", "testItem description")
        val user = createTestUser()
        val expectedUserId = user.id

        given(fetchCurrentUserUsecase.execute()).willReturn(user)

        createItemUsecaseImpl.execute(request)

        then(itemRepository).should().save(check {
            assertAll(
                { assertEquals(request.itemName, it.name) },
                { assertEquals(request.coupangUrl, it.coupangUrl) },
                { assertEquals(request.description, it.description) },
                { assertEquals(expectedUserId, it.user.id) },
            )
        })
    }
}