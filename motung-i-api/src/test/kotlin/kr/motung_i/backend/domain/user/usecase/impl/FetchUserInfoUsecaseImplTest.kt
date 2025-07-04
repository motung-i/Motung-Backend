package kr.motung_i.backend.domain.user.usecase.impl

import kr.motung_i.backend.domain.user.usecase.FetchCurrentUserUsecase
import kr.motung_i.backend.testfixture.UserFixture.createTestUser
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.kotlin.given
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
class FetchUserInfoUsecaseImplTest {
    @Mock
    private lateinit var fetchCurrentUserUsecase: FetchCurrentUserUsecase

    @InjectMocks
    private lateinit var fetchUserInfoUsecaseImpl: FetchUserInfoUsecaseImpl

    @Test
    @DisplayName("유저 정보를 조회하면 올바른 DTO로 반환한다")
    fun 유저_정보를_조회하면_올바른_DTO로_반환한다() {
        val user = createTestUser()

        given(fetchCurrentUserUsecase.execute()).willReturn(user)

        val result = fetchUserInfoUsecaseImpl.execute()

        assertAll(
            { assertEquals(user.nickname, result.nickname) },
            { assertEquals(user.role, result.role) },
        )
    }
}