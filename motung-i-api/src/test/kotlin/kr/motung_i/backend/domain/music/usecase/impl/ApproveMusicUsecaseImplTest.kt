package kr.motung_i.backend.domain.music.usecase.impl

import kr.motung_i.backend.domain.music.presentation.dto.request.ApproveMusicRequest
import kr.motung_i.backend.global.exception.CustomException
import kr.motung_i.backend.global.exception.enums.CustomErrorCode
import kr.motung_i.backend.persistence.music.entity.enums.MusicStatus
import kr.motung_i.backend.persistence.music.repository.MusicRepository
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
import java.util.UUID

@ExtendWith(MockitoExtension::class)
class ApproveMusicUsecaseImplTest {
    @Mock
    private lateinit var musicRepository: MusicRepository

    @InjectMocks
    private lateinit var approveMusicUsecaseImpl: ApproveMusicUsecaseImpl

    @Test
    @DisplayName("노래를 승인하면 승인 상태로 변경한다")
    fun 노래를_승인하면_승인_상태로_변경한다() {
        val request = ApproveMusicRequest(1)
        val music = createTestMusic()

        given(musicRepository.findByRankNumber(any<Int>())).willReturn(null)
        given(musicRepository.findById(any<UUID>())).willReturn(music)

        approveMusicUsecaseImpl.execute(music.id!!, request)

        assertEquals(MusicStatus.APPROVED, music.musicStatus)
    }

    @Test
    @DisplayName("정상적으로 요청을 처리하면 요청한 순위로 노래 순위를 변경한다")
    fun 정상적으로_요청을_처리하면_요청한_순위로_노래_순위를_변경한다() {
        val request = ApproveMusicRequest(1)
        val music = createTestMusic()

        given(musicRepository.findByRankNumber(any<Int>())).willReturn(null)
        given(musicRepository.findById(any<UUID>())).willReturn(music)

        approveMusicUsecaseImpl.execute(music.id!!, request)

        assertEquals(request.rankNumber, music.rankNumber)
    }

    @Test
    @DisplayName("정상적으로 요청을 처리하면 요청한 순위에 있는 노래는 대기 상태로 변경한다")
    fun 정상적으로_요청을_처리하면_요청한_순위에_있는_노래는_대기_상태로_변경한다() {
        val request = ApproveMusicRequest(1)
        val oldMusic = createTestMusic(rankNumber = request.rankNumber)
        val newMusic = createTestMusic()

        given(musicRepository.findByRankNumber(any<Int>())).willReturn(oldMusic)
        given(musicRepository.findById(any<UUID>())).willReturn(newMusic)

        approveMusicUsecaseImpl.execute(newMusic.id!!, request)

        assertEquals(MusicStatus.PENDING, oldMusic.musicStatus)
    }

    @Test
    @DisplayName("정상적으로 요청을 처리하면 요청한 순위에 있는 아이템은 꿀템 순위에서 제거한다")
    fun 정상적으로_요청을_처리하면_요청한_순위에_있는_아이템은_꿀템_순위에서_제거한다() {
        val request = ApproveMusicRequest(1)
        val oldMusic = createTestMusic(rankNumber = request.rankNumber)
        val newMusic = createTestMusic()

        given(musicRepository.findByRankNumber(any<Int>())).willReturn(oldMusic)
        given(musicRepository.findById(any<UUID>())).willReturn(newMusic)

        approveMusicUsecaseImpl.execute(newMusic.id!!, request)

        assertNull(oldMusic.rankNumber)
    }

    @Test
    @DisplayName("요청한 아이템이 이미 순위에 존재하면 예외가 발생한다")
    fun 요청한_아이템이_이미_순위에_존재하면_예외가_발생한다() {
        val request = ApproveMusicRequest(1)
        val music = createTestMusic(rankNumber = request.rankNumber)

        given(musicRepository.findByRankNumber(any<Int>())).willReturn(music)
        given(musicRepository.findById(any<UUID>())).willReturn(music)

        val throws = assertThrows<CustomException> {
            approveMusicUsecaseImpl.execute(music.id!!, request)
        }

        assertEquals(CustomErrorCode.MUSIC_ALREADY_APPROVED, throws.customErrorCode)
    }
}