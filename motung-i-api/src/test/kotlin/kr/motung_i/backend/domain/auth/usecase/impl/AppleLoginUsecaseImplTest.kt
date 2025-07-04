package kr.motung_i.backend.domain.auth.usecase.impl

import kr.motung_i.backend.domain.auth.presentation.dto.request.AppleLoginRequest
import kr.motung_i.backend.domain.user.usecase.CreateUserUsecase
import kr.motung_i.backend.global.security.provider.JwtTokenProvider
import kr.motung_i.backend.global.third_party.apple.dto.FetchDataFromAppleRequest
import kr.motung_i.backend.global.third_party.apple.service.AppleService
import kr.motung_i.backend.persistence.auth.repository.RefreshTokenCustomRepository
import kr.motung_i.backend.persistence.device_token.DeviceTokenRepository
import kr.motung_i.backend.persistence.device_token.entity.DeviceToken
import kr.motung_i.backend.persistence.user.entity.User
import kr.motung_i.backend.persistence.user.entity.enums.Role
import kr.motung_i.backend.persistence.user.repository.UserRepository
import kr.motung_i.backend.testfixture.AuthFixture.createTestDeviceToken
import kr.motung_i.backend.testfixture.UserFixture.createTestUser
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers.anyBoolean
import org.mockito.ArgumentMatchers.anyString
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.any
import org.mockito.kotlin.given
import org.mockito.kotlin.never
import org.mockito.kotlin.then
import java.util.UUID
import kotlin.test.assertTrue

@ExtendWith(MockitoExtension::class)
class AppleLoginUsecaseImplTest {
    @Mock
    private lateinit var jwtTokenProvider: JwtTokenProvider
    @Mock
    private lateinit var userRepository: UserRepository
    @Mock
    private lateinit var createUserUsecase: CreateUserUsecase
    @Mock
    private lateinit var deviceTokenRepository: DeviceTokenRepository
    @Mock
    private lateinit var refreshTokenRepository: RefreshTokenCustomRepository
    @Mock
    private lateinit var appleService: AppleService

    @InjectMocks
    private lateinit var appleLoginUsecaseImpl: AppleLoginUsecaseImpl

    @Test
    @DisplayName("애플 로그인을 성공하면 토큰을 반환한다")
    fun 애플_로그인을_성공하면_토큰을_반환한다() {
        val request = AppleLoginRequest("Bearer TestToken", null)
        val user = createTestUser()

        setupDefaultMocks(user)
        given(userRepository.findByOauthId(anyString())).willReturn(user)

        val result = appleLoginUsecaseImpl.execute(request)

        assertTrue(result.accessToken.isNotBlank())
        assertTrue(result.refreshToken.isNotBlank())
    }

    @Test
    @DisplayName("처음 로그인하면 정보를 저장한다")
    fun 처음_로그인하면_정보를_저장한다() {
        val request = AppleLoginRequest("Bearer TestToken", null)
        val user = createTestUser()

        setupDefaultMocks(user)
        given(userRepository.findByOauthId(anyString())).willReturn(null)
        given(createUserUsecase.execute(any<User>())).willReturn(user)

        appleLoginUsecaseImpl.execute(request)

        then(createUserUsecase).should().execute(any<User>())
    }

    @Test
    @DisplayName("정보가 저장 된 유저가 로그인하면 정보를 저장하지 않는다")
    fun 정보가_저장_된_유저가_로그인하면_정보를_저장하지_않는다() {
        val request = AppleLoginRequest("Bearer TestToken", null)
        val user = createTestUser()

        setupDefaultMocks(user)
        given(userRepository.findByOauthId(anyString())).willReturn(user)

        appleLoginUsecaseImpl.execute(request)

        then(userRepository).should(never()).save(any())
    }

    @Test
    @DisplayName("디바이스 토큰이 있으면 디바이스 토큰을 저장한다")
    fun 디바이스_토큰이_있으면_디바이스_토큰을_저장한다() {
        val request = AppleLoginRequest("Bearer TestToken", "DeviceToken")
        val user = createTestUser()

        setupDefaultMocks(user)
        given(userRepository.findByOauthId(anyString())).willReturn(user)
        given(deviceTokenRepository.save(any<DeviceToken>()))
            .willReturn(createTestDeviceToken(user = user))

        appleLoginUsecaseImpl.execute(request)

        then(deviceTokenRepository).should().save(any<DeviceToken>())
    }

    @Test
    @DisplayName("디바이스 토큰이 없으면 디바이스 토큰을 저장하지 않는다")
    fun 디바이스_토큰이_없으면_디바이스_토큰을_저장하지_않는다() {
        val request = AppleLoginRequest("Bearer TestToken", null)
        val user = createTestUser()

        setupDefaultMocks(user)
        given(userRepository.findByOauthId(anyString())).willReturn(user)

        appleLoginUsecaseImpl.execute(request)

        then(deviceTokenRepository).should(never()).save(any<DeviceToken>())
    }

    private fun setupDefaultMocks(user: User) {
        given(jwtTokenProvider.generateToken(any<UUID>(), any<Role>(), anyBoolean()))
            .willReturn("Bearer TestToken")
        given(appleService.getAppleInfoFromIdToken(anyString()))
            .willReturn(FetchDataFromAppleRequest(user.oauthId, "Bearer TestToken",user.email!!))
    }
}