package kr.motung_i.backend.domain.auth.usecase.impl

import kr.motung_i.backend.domain.auth.presentation.dto.response.impl.TokenRequest
import kr.motung_i.backend.domain.user.usecase.FetchCurrentUserUsecase
import kr.motung_i.backend.global.exception.CustomException
import kr.motung_i.backend.global.exception.enums.CustomErrorCode
import kr.motung_i.backend.global.security.provider.JwtTokenProvider
import kr.motung_i.backend.persistence.auth.repository.RefreshTokenCustomRepository
import kr.motung_i.backend.testfixture.UserFixture.createTestUser
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.any
import org.mockito.kotlin.given
import org.mockito.kotlin.then
import kotlin.test.Test
import kotlin.test.assertEquals

@ExtendWith(MockitoExtension::class)
class LogoutUsecaseImplTest {
    @Mock
    private lateinit var fetchCurrentUserUsecase: FetchCurrentUserUsecase
    @Mock
    private lateinit var jwtTokenProvider: JwtTokenProvider
    @Mock
    private lateinit var refreshTokenCustomRepository: RefreshTokenCustomRepository

    @InjectMocks
    private lateinit var logoutUsecaseImpl: LogoutUsecaseImpl

    @Test
    @DisplayName("요청한 유저와 토큰의 유저 정보가 같으면 리프레쉬 토큰을 삭제한다")
    fun 요청한_유저와_토큰의_유저_정보가_같으면_리프레쉬_토큰을_삭제한다() {
        val request = TokenRequest("Bearer TestToken")
        val userA = createTestUser()

        given(fetchCurrentUserUsecase.execute()).willReturn(userA)
        given(jwtTokenProvider.getUserId(any<String>(), any<Boolean>()))
            .willReturn(userA.id.toString())

        logoutUsecaseImpl.execute(request)

        then(refreshTokenCustomRepository).should().delete(any<String>())
    }

    @Test
    @DisplayName("요청한 유저와 토큰의 유저 정보가 다르면 예외가 발생한다")
    fun 요청한_유저와_토큰의_유저_정보가_다르면_예외가_발생한다() {
        val request = TokenRequest("Bearer BadTestToken")
        val userA = createTestUser()
        val userB = createTestUser()

        given(fetchCurrentUserUsecase.execute()).willReturn(userA)
        given(jwtTokenProvider.getUserId(any<String>(), any<Boolean>()))
            .willReturn(userB.id.toString())

        val throws = assertThrows<CustomException> {
            logoutUsecaseImpl.execute(request)
        }

        assertEquals(CustomErrorCode.NOT_FOUND_USER, throws.customErrorCode)
    }
}