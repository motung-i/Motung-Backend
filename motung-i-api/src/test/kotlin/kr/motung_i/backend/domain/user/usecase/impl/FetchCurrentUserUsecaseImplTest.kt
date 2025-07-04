package kr.motung_i.backend.domain.user.usecase.impl

import kr.motung_i.backend.testfixture.UserFixture.createTestUser
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder

@ExtendWith(MockitoExtension::class)
class FetchCurrentUserUsecaseImplTest {
    @InjectMocks
    private lateinit var fetchCurrentUserUsecaseImpl: FetchCurrentUserUsecaseImpl

    @Test
    @DisplayName("시큐리티 콘텍스트에 저장된 유저를 올바르게 반환한다")
    fun 시큐리티_콘텍스트에_저장된_유저를_올바르게_반환한다() {
        val user = createTestUser()
        SecurityContextHolder.getContext().authentication = UsernamePasswordAuthenticationToken(user, null, listOf())

        val result = fetchCurrentUserUsecaseImpl.execute()

        assertEquals(user.id, result.id)
    }
}