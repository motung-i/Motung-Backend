package kr.motung_i.backend.domain.music.usecase.impl

import kr.motung_i.backend.domain.music.presentation.dto.request.RemoveMusicRequest
import kr.motung_i.backend.domain.user.usecase.SuspensionUserUsecase
import kr.motung_i.backend.global.exception.CustomException
import kr.motung_i.backend.global.exception.enums.CustomErrorCode
import kr.motung_i.backend.persistence.music.repository.MusicRepository
import kr.motung_i.backend.persistence.review_report.entity.ReportReason
import kr.motung_i.backend.persistence.user.entity.User
import kr.motung_i.backend.persistence.user_suspension.entity.SuspensionPeriod
import kr.motung_i.backend.persistence.user_suspension.entity.SuspensionTarget
import kr.motung_i.backend.testfixture.MusicFixture.createTestMusic
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.any
import org.mockito.kotlin.given
import org.mockito.kotlin.then
import java.util.UUID

@ExtendWith(MockitoExtension::class)
class RemoveMusicUsecaseImplTest {
    @Mock
    private lateinit var musicRepository: MusicRepository
    @Mock
    private lateinit var suspensionUserUsecase: SuspensionUserUsecase

    @InjectMocks
    private lateinit var removeMusicUsecaseImpl: RemoveMusicUsecaseImpl

    @Test
    @DisplayName("노래를 정상 삭제하면 delete를 호출한다")
    fun 노래를_정상_삭제하면_delete를_호출한다() {
        val request = RemoveMusicRequest(null)
        val music = createTestMusic()

        given(musicRepository.findById(any<UUID>())).willReturn(music)

        removeMusicUsecaseImpl.execute(music.id!!, request)

        then(musicRepository).should().delete(music)
    }

    @Test
    @DisplayName("노래를 정상 삭제하면 suspensionUserUsecase를 호출한다")
    fun 노래를_정상_삭제하면_suspensionUserUsecase를_호출한다() {
        val request = RemoveMusicRequest(SuspensionPeriod.entries.random())
        val music = createTestMusic()

        given(musicRepository.findById(any<UUID>())).willReturn(music)

        removeMusicUsecaseImpl.execute(music.id!!, request)

        then(suspensionUserUsecase).should().execute(
            any<User>(),
            any<SuspensionTarget>(),
            any<Set<ReportReason>>(),
            any<SuspensionPeriod>()
        )
    }

    @Test
    @DisplayName("승인된 노래를 삭제하면 예외가 발생한다")
    fun 승인된_노래를_삭제하면_예외가_발생한다() {
        val request = RemoveMusicRequest(null)
        val music = createTestMusic(rankNumber = 1)

        given(musicRepository.findById(any<UUID>())).willReturn(music)

        val throws = assertThrows<CustomException> {
            removeMusicUsecaseImpl.execute(music.id!!, request)
        }

        assertEquals(CustomErrorCode.CANNOT_MODIFY_APPROVED_MUSIC, throws.customErrorCode)
    }

    @Test
    @DisplayName("존재하지 않는 노래를 요청하면 예외가 발생한다")
    fun 존재하지_않는_노래를_요청하면_예외가_발생한다() {
        val notExistMusicId = UUID.randomUUID()
        val request = RemoveMusicRequest(null)

        given(musicRepository.findById(notExistMusicId)).willReturn(null)

        val throws = assertThrows<CustomException> {
            removeMusicUsecaseImpl.execute(notExistMusicId, request)
        }

        assertEquals(CustomErrorCode.NOT_FOUND_MUSIC, throws.customErrorCode)
    }
}