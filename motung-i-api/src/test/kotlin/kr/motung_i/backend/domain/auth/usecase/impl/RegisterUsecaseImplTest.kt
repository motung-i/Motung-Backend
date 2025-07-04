package kr.motung_i.backend.domain.auth.usecase.impl

import kr.motung_i.backend.domain.auth.presentation.dto.response.impl.RegisterRequest
import kr.motung_i.backend.domain.user.usecase.FetchCurrentUserUsecase
import kr.motung_i.backend.persistence.user.entity.User
import kr.motung_i.backend.persistence.user.entity.enums.Role
import kr.motung_i.backend.persistence.user.repository.UserRepository
import kr.motung_i.backend.testfixture.UserFixture.createTestUser
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.any
import org.mockito.kotlin.given
import org.mockito.kotlin.then

@ExtendWith(MockitoExtension::class)
class RegisterUsecaseImplTest {
    @Mock
    private lateinit var userRepository: UserRepository
    @Mock
    private lateinit var fetchCurrentUserUsecase: FetchCurrentUserUsecase

    @InjectMocks
    private lateinit var registerUsecaseImpl: RegisterUsecaseImpl

    @Test
    @DisplayName("정상적으로 요청이 처리되면 입력한 회원정보를 저장한다")
    fun 정상적으로_요청이_처리되면_입력한_회원정보를_저장한다() {
        val request = RegisterRequest("nickname")

        given(fetchCurrentUserUsecase.execute()).willReturn(createTestUser())

        registerUsecaseImpl.execute(request)

        then(userRepository).should().save(any<User>())
    }

    @Test
    @DisplayName("정상적으로 요청이 처리되면 입력한 회원정보를 반영한다")
    fun 정상적으로_요청이_처리되면_입력한_회원정보를_반영한다() {
        val request = RegisterRequest("nickname")
        val user = createTestUser()

        given(fetchCurrentUserUsecase.execute()).willReturn(user)

        registerUsecaseImpl.execute(request)

        assertEquals(request.nickname, user.nickname)
    }

    @Test
    @DisplayName("정상적으로 요청이 처리되면 권한을 USER로 변경한다")
    fun 정상적으로_요청이_처리되면_권한을_USER로_변경한다() {
        val request = RegisterRequest("nickname")
        val user = createTestUser()

        given(fetchCurrentUserUsecase.execute()).willReturn(user)

        registerUsecaseImpl.execute(request)

        assertEquals(Role.ROLE_USER, user.role)
    }

}