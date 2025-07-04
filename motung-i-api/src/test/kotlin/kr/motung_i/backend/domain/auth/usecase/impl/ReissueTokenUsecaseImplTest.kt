package kr.motung_i.backend.domain.auth.usecase.impl

import kr.motung_i.backend.domain.auth.presentation.dto.response.impl.TokenRequest
import kr.motung_i.backend.global.exception.CustomException
import kr.motung_i.backend.global.exception.enums.CustomErrorCode
import kr.motung_i.backend.global.security.provider.JwtTokenProvider
import kr.motung_i.backend.persistence.auth.entity.RefreshToken
import kr.motung_i.backend.persistence.auth.repository.RefreshTokenCustomRepository
import kr.motung_i.backend.persistence.user.entity.enums.Role
import kr.motung_i.backend.persistence.user.repository.UserRepository
import kr.motung_i.backend.testfixture.AuthFixture.createTestRefreshToken
import kr.motung_i.backend.testfixture.UserFixture.createTestUser
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.any
import org.mockito.kotlin.given
import org.mockito.kotlin.then
import java.util.Optional
import java.util.UUID

@ExtendWith(MockitoExtension::class)
class ReissueTokenUsecaseImplTest {
    @Mock
    private lateinit var userRepository: UserRepository
    @Mock
    private lateinit var refreshTokenCustomRepository: RefreshTokenCustomRepository
    @Mock
    private lateinit var jwtTokenProvider: JwtTokenProvider

    @InjectMocks
    private lateinit var reissueTokenUsecaseImpl: ReissueTokenUsecaseImpl

    @Test
    fun 토큰_재발급을_성공하면_모든_토큰을_반환한다() {
        val request = TokenRequest("Bearer TestToken")
        val user = createTestUser()

        given(refreshTokenCustomRepository.find(any<String>()))
            .willReturn(Optional.of(createTestRefreshToken(user)))
        given(userRepository.findByUserId(any<UUID>()))
            .willReturn(Optional.of(user))
        given(jwtTokenProvider.generateToken(any<UUID>(), any<Role>(), any<Boolean>()))
            .willReturn("Bearer TestToken")

        val result = reissueTokenUsecaseImpl.execute(request)

        assertTrue(result.accessToken.isNotBlank())
        assertTrue(result.refreshToken.isNotBlank())
    }

    @Test
    fun 토큰_재발급을_성공하면_기존_리프레시_토큰을_삭제한다() {
        val request = TokenRequest("Bearer TestToken")
        val user = createTestUser()

        given(refreshTokenCustomRepository.find(any<String>()))
            .willReturn(Optional.of(createTestRefreshToken(user)))
        given(userRepository.findByUserId(any<UUID>()))
            .willReturn(Optional.of(user))
        given(jwtTokenProvider.generateToken(any<UUID>(), any<Role>(), any<Boolean>()))
            .willReturn("Bearer TestToken")

        reissueTokenUsecaseImpl.execute(request)

        then(refreshTokenCustomRepository).should().delete(any<String>())
    }

    @Test
    fun 토큰_재발급을_성공하면_리프레시_토큰을_저장한다() {
        val request = TokenRequest("Bearer TestToken")
        val user = createTestUser()

        given(refreshTokenCustomRepository.find(any<String>()))
            .willReturn(Optional.of(createTestRefreshToken(user)))
        given(userRepository.findByUserId(any<UUID>()))
            .willReturn(Optional.of(user))
        given(jwtTokenProvider.generateToken(any<UUID>(), any<Role>(), any<Boolean>()))
            .willReturn("Bearer TestToken")

        reissueTokenUsecaseImpl.execute(request)

        then(refreshTokenCustomRepository).should().save(any<RefreshToken>())
    }

    @Test
    fun 존재하지_않는_리프레시_토큰으로_요청하면_예외가_발생한다() {
        val request = TokenRequest("Bearer NotExistingTestToken")

        given(refreshTokenCustomRepository.find(request.refreshToken))
            .willReturn(Optional.empty())

        val assertThrows = assertThrows<CustomException> {
            reissueTokenUsecaseImpl.execute(request)
        }

        assertEquals(CustomErrorCode.UNAUTHORIZED, assertThrows.customErrorCode)
    }

    @Test
    fun 존재하지_않는_유저의_정보가_있는_리프레시_토큰으로_요청하면_예외가_발생한다() {
        val request = TokenRequest("Bearer TestToken")
        val notExistingUser = createTestUser()

        given(refreshTokenCustomRepository.find(request.refreshToken))
            .willReturn(Optional.of(createTestRefreshToken(notExistingUser)))
        given(userRepository.findByUserId(notExistingUser.id!!))
            .willReturn(Optional.empty())

        val assertThrows = assertThrows<CustomException> {
            reissueTokenUsecaseImpl.execute(request)
        }

        assertEquals(CustomErrorCode.NOT_FOUND_USER, assertThrows.customErrorCode)
    }
}