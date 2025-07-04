package kr.motung_i.backend.domain.auth.usecase.impl

import kr.motung_i.backend.domain.user.usecase.FetchCurrentUserUsecase
import kr.motung_i.backend.testfixture.UserFixture.createTestUser
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.BDDMockito.*
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import kotlin.test.assertFalse
import kotlin.test.assertTrue

@ExtendWith(MockitoExtension::class)
class CheckIsUserRegisterUsecaseImplTest {
    @Mock
    private lateinit var fetchCurrentUserUsecase: FetchCurrentUserUsecase

    @InjectMocks
    private lateinit var checkIsUserRegisterUsecaseImpl: CheckIsUserRegisterUsecaseImpl

    @Test
    @DisplayName("닉네임이 없으면 false를 반환한다")
    fun 닉네임이_없으면_false를_반환한다() {
        given(fetchCurrentUserUsecase.execute()).willReturn(createTestUser())

        val result = checkIsUserRegisterUsecaseImpl.execute()

        assertFalse(result.isUserRegistered)
    }

    @Test
    @DisplayName("닉네임 있으면 true를 반환한다")
    fun 닉네임_있으면_true를_반환한다() {
        given(fetchCurrentUserUsecase.execute()).willReturn(createTestUser(nickName = "nickname"))

        val result = checkIsUserRegisterUsecaseImpl.execute()

        assertTrue(result.isUserRegistered)
    }
}