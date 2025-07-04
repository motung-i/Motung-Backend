package kr.motung_i.backend.domain.user.usecase.impl

import kr.motung_i.backend.domain.user.usecase.FetchCurrentUserUsecase
import kr.motung_i.backend.global.exception.CustomException
import kr.motung_i.backend.global.exception.enums.CustomErrorCode
import kr.motung_i.backend.persistence.review_report.entity.ReportReason
import kr.motung_i.backend.persistence.user.entity.User
import kr.motung_i.backend.persistence.user_suspension.entity.SuspensionPeriod
import kr.motung_i.backend.persistence.user_suspension.entity.SuspensionTarget
import kr.motung_i.backend.persistence.user_suspension.entity.UserSuspension
import kr.motung_i.backend.persistence.user_suspension.repository.UserSuspensionRepository
import kr.motung_i.backend.testfixture.UserFixture.createTestUser
import kr.motung_i.backend.testfixture.UserFixture.createTestUserSuspension
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
import org.mockito.kotlin.never
import org.mockito.kotlin.then

@ExtendWith(MockitoExtension::class)
class SuspensionUserUsecaseImplTest {
    @Mock
    private lateinit var userSuspensionRepository: UserSuspensionRepository
    @Mock
    private lateinit var fetchCurrentUserUsecase: FetchCurrentUserUsecase

    @InjectMocks
    private lateinit var suspensionUserUsecaseImpl: SuspensionUserUsecaseImpl

    @Test
    @DisplayName("유저를 정지하면 정지 기록을 올바른 데이터로 저장한다")
    fun 유저를_정지하면_정지_기록을_올바른_데이터로_저장한다() {
        val user = createTestUser()
        val expectedUserId = user.id
        val suspensionTarget = SuspensionTarget.entries.random()
        val reasons = setOf(ReportReason.entries.first())
        val suspensionPeriod = SuspensionPeriod.entries.random()

        given(fetchCurrentUserUsecase.execute()).willReturn(user)
        given(userSuspensionRepository.findWithReasonsByUser(any<User>()))
            .willReturn(null)

        suspensionUserUsecaseImpl.execute(user, suspensionTarget, reasons, suspensionPeriod)

        then(userSuspensionRepository).should().save(check {
            assertAll(
                { assertEquals(expectedUserId, it.user.id) },
                { assertEquals(suspensionTarget, it.target) },
                { assertEquals(reasons.first(), it.reasons.first()) },
                { assertEquals(suspensionPeriod, it.suspensionPeriod) },
                { assertEquals(expectedUserId, it.suspendedBy!!.id) },
            )
        })
    }

    @Test
    @DisplayName("가지고 있는 정지기간 보다 큰 정지 기간을 저장하면 정지 기록을 덮어씌운다")
    fun 가지고_있는_정지기간_보다_큰_정지_기간을_저장하면_정지_기록을_덮어씌운다() {
        val user = createTestUser()
        val expectedUserId = user.id
        val newSuspensionTarget = SuspensionTarget.entries.random()
        val newReasons = setOf(ReportReason.entries.first())
        val newSuspensionPeriod = SuspensionPeriod.PERMANENT

        val savedUserSuspension = createTestUserSuspension(
            user = user,
            suspensionPeriod = SuspensionPeriod.ONE,
        )

        given(fetchCurrentUserUsecase.execute()).willReturn(user)
        given(userSuspensionRepository.findWithReasonsByUser(any<User>()))
            .willReturn(savedUserSuspension)

        suspensionUserUsecaseImpl.execute(user, newSuspensionTarget, newReasons, newSuspensionPeriod)

        then(userSuspensionRepository).should().save(check {
            assertAll(
                { assertEquals(expectedUserId, it.user.id) },
                { assertEquals(newSuspensionTarget, it.target) },
                { assertEquals(newReasons.first(), it.reasons.first()) },
                { assertEquals(newSuspensionPeriod, it.suspensionPeriod) },
                { assertEquals(expectedUserId, it.suspendedBy!!.id) },
            )
        })
    }

    @Test
    @DisplayName("가지고 있는 정지기간 보다 작은 정지 기간을 요청하면 저장하지 않는다")
    fun 가지고_있는_정지기간_보다_작은_정지_기간을_요청하면_저장하지_않는다() {
        val user = createTestUser()
        val newSuspensionTarget = SuspensionTarget.entries.random()
        val newReasons = setOf(ReportReason.entries.first())
        val newSuspensionPeriod = SuspensionPeriod.ONE

        val savedUserSuspension = createTestUserSuspension(
            user = user,
            suspensionPeriod = SuspensionPeriod.PERMANENT,
        )

        given(fetchCurrentUserUsecase.execute()).willReturn(user)
        given(userSuspensionRepository.findWithReasonsByUser(any<User>()))
            .willReturn(savedUserSuspension)

        suspensionUserUsecaseImpl.execute(user, newSuspensionTarget, newReasons, newSuspensionPeriod)

        then(userSuspensionRepository).should(never()).save(any<UserSuspension>())

    }

    @Test
    @DisplayName("정지 기간이 존재하지 않으면 기록을 저장하지 않는다")
    fun 정지_기간이_존재하지_않으면_기록을_저장하지_않는다() {
        val user = createTestUser()
        val suspensionTarget = SuspensionTarget.entries.random()
        val reasons = setOf(ReportReason.entries.first())
        val suspensionPeriod = null

        suspensionUserUsecaseImpl.execute(user, suspensionTarget, reasons, suspensionPeriod)

        then(userSuspensionRepository).should(never()).save(any<UserSuspension>())
    }

    @Test
    @DisplayName("관리자를 정지하면 예외가 발생한다")
    fun 관리자를_정지하면_예외가_발생한다() {
        val user = createTestUser(isAdmin = true)
        val suspensionTarget = SuspensionTarget.entries.random()
        val reasons = setOf(ReportReason.entries.first())
        val suspensionPeriod = SuspensionPeriod.entries.random()


        val throws = assertThrows<CustomException> {
            suspensionUserUsecaseImpl.execute(user, suspensionTarget, reasons, suspensionPeriod)
        }

        assertEquals(CustomErrorCode.SUSPENDED_ADMIN, throws.customErrorCode)
    }
}