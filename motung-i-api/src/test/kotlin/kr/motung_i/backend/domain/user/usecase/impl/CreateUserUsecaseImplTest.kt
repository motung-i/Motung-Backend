package kr.motung_i.backend.domain.user.usecase.impl

import kr.motung_i.backend.persistence.user.entity.enums.Provider
import kr.motung_i.backend.persistence.user.repository.UserRepository
import kr.motung_i.backend.testfixture.UserFixture.createTestUser
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.any
import org.mockito.kotlin.check
import org.mockito.kotlin.given
import org.mockito.kotlin.then

@ExtendWith(MockitoExtension::class)
class CreateUserUsecaseImplTest {
    @Mock
    private lateinit var userRepository: UserRepository

    @InjectMocks
    private lateinit var createUserUsecase: CreateUserUsecaseImpl

    @Test
    @DisplayName("유저를 저장하면 올바른 데이터로 저장한다")
    fun 유저를_저장하면_올바른_데이터로_저장한다() {
        val user = createTestUser()

        val expectedUserId = user.id
        val expectedOauthId = user.oauthId
        val expectedProvider = user.provider
        val expectedEmail = user.email

        createUserUsecase.execute(user)

        then(userRepository).should().save(check {
            assertAll(
                { assertEquals(expectedUserId, it.id) },
                { assertEquals(expectedOauthId, it.oauthId) },
                { assertEquals(expectedProvider, it.provider) },
                { assertEquals(expectedEmail, it.email) },
            )
        })
    }

    @Test
    @DisplayName("회원 탈퇴했던 유저를 저장하면 탈퇴 전의 데이터를 덮어씌워서 저장한다")
    fun 회원_탈퇴했던_유저를_저장하면_탈퇴_전의_데이터를_덮어씌워서_저장한다() {
        val oauthId = "savedOauthId"
        val provider = Provider.entries.random()
        val removedUser = createTestUser(oauthId = oauthId, provider = provider)
            .also { it.remove() }
        val newUser = createTestUser(oauthId = oauthId, provider = provider)

        val expectedUserId = removedUser.id
        val expectedOauthId = newUser.oauthId
        val expectedProvider = newUser.provider
        val expectedEmail = newUser.email

        given(userRepository.findByOauthIdAndProvider(any<String>(), any<Provider>()))
            .willReturn(removedUser)

        createUserUsecase.execute(newUser)

        then(userRepository).should().save(check {
            assertAll(
                { assertEquals(expectedUserId, it.id) },
                { assertEquals(expectedOauthId, it.oauthId) },
                { assertEquals(expectedProvider, it.provider) },
                { assertEquals(expectedEmail, it.email) },
            )
        })
    }
}