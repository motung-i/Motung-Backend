package kr.motung_i.backend.domain.user.usecase.impl

import kr.motung_i.backend.domain.user.usecase.FetchCurrentUserUsecase
import kr.motung_i.backend.persistence.user.repository.UserRepository
import kr.motung_i.backend.persistence.user.util.HashUtil.Companion.sha256
import kr.motung_i.backend.persistence.user.util.HashUtil.Companion.toHex
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
class RemoveUserUsecaseImplTest {
    @Mock
    private lateinit var userRepository: UserRepository
    @Mock
    private lateinit var fetchCurrentUserUsecase: FetchCurrentUserUsecase

    @InjectMocks
    private lateinit var removeUserUsecaseImpl: RemoveUserUsecaseImpl

    @Test
    @DisplayName("유저를 삭제하면 개인정보만 삭제한다")
    fun 유저를_삭제하면_개인정보만_삭제한다() {
        val user = createTestUser()
        val expectedUserId = user.id
        val expectedProvider = user.provider
        val expectedOauthId = user.oauthId.sha256().toHex()

        given(fetchCurrentUserUsecase.execute()).willReturn(user)

        removeUserUsecaseImpl.execute()

        then(userRepository).should().save(check {
            assertAll(
                { assertEquals(expectedUserId, it.id) },
                { assertEquals(expectedProvider, it.provider) },
                { assertEquals(expectedOauthId, it.oauthId) },
                { assertNull(it.email) },
                { assertNull(it.nickname) },
            )
        })
    }

}