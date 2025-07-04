package kr.motung_i.backend.domain.user.usecase.impl

import kr.motung_i.backend.domain.user.presentation.dto.request.UpdateNicknameRequest
import kr.motung_i.backend.domain.user.usecase.FetchCurrentUserUsecase
import kr.motung_i.backend.global.exception.CustomException
import kr.motung_i.backend.global.exception.enums.CustomErrorCode
import kr.motung_i.backend.persistence.user.repository.UserRepository
import kr.motung_i.backend.testfixture.UserFixture.createTestUser
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.any
import org.mockito.kotlin.check
import org.mockito.kotlin.given
import org.mockito.kotlin.then

@ExtendWith(MockitoExtension::class)
class UpdateNicknameUsecaseImplTest {
    @Mock
    private lateinit var fetchCurrentUserUsecase: FetchCurrentUserUsecase
    @Mock
    private lateinit var userRepository: UserRepository

    @InjectMocks
    private lateinit var updateNicknameUsecaseImpl: UpdateNicknameUsecaseImpl

    @Test
    @DisplayName("닉네임을 변경하면 올바른 데이터로 저장한다")
    fun 닉네임을_변경하면_올바른_데이터로_저장한다() {
        val request = UpdateNicknameRequest("newNickname")
        val user = createTestUser()

        given(fetchCurrentUserUsecase.execute()).willReturn(user)
        given(userRepository.existsByNickname(any<String>())).willReturn(false)

        updateNicknameUsecaseImpl.execute(request)

        then(userRepository).should().save(check {
            assertEquals(request.nickname, user.nickname)
        })
    }

    @Test
    @DisplayName("동일한 닉네임을 이미 사용중인 유저가 있다면 예외가 발생한다")
    fun 동일한_닉네임을_이미_사용중인_유저가_있다면_예외가_발생한다() {
        val request = UpdateNicknameRequest("existingNickname")
        val user = createTestUser()

        given(fetchCurrentUserUsecase.execute()).willReturn(user)
        given(userRepository.existsByNickname(any<String>())).willReturn(true)

        val throws = assertThrows<CustomException> {
            updateNicknameUsecaseImpl.execute(request)
        }

        assertEquals(CustomErrorCode.ALREADY_EXISTS_NICKNAME, throws.customErrorCode)
    }
}