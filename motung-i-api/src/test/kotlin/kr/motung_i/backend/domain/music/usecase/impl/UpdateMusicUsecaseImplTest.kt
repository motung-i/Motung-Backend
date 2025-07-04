package kr.motung_i.backend.domain.music.usecase.impl

import kr.motung_i.backend.domain.music.presentation.dto.request.UpdateMusicRequest
import kr.motung_i.backend.global.exception.CustomException
import kr.motung_i.backend.global.exception.enums.CustomErrorCode
import kr.motung_i.backend.global.third_party.youtube.YoutubeAdapter
import kr.motung_i.backend.persistence.music.repository.MusicRepository
import kr.motung_i.backend.testfixture.MusicFixture.createTestMusic
import kr.motung_i.backend.testfixture.MusicFixture.createTestVideo
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
import java.util.UUID

@ExtendWith(MockitoExtension::class)
class UpdateMusicUsecaseImplTest {
    @Mock
    private lateinit var musicRepository: MusicRepository
    @Mock
    private lateinit var youtubeAdapter: YoutubeAdapter

    @InjectMocks
    private lateinit var updateMusicUsecaseImpl: UpdateMusicUsecaseImpl

    @Test
    @DisplayName("정상적으로 요청을 처리하면 노래에 변경사항이 반영된다")
    fun 정상적으로_요청을_처리하면_노래에_변경사항이_반영된다() {
        val request = UpdateMusicRequest(
            title = "newTestMusic",
            singer = "testSinger",
            youtubeUrl = "https://youtuber.com/new",
            description = "newTestMusic description"
        )
        val music = createTestMusic()
        val testVideo = createTestVideo()
        val thumbnailUrl = testVideo.items.first().snippet.thumbnails.medium.url

        given(musicRepository.findById(any<UUID>())).willReturn(music)
        given(youtubeAdapter.fetchVideo(any<String>())).willReturn(testVideo)

        updateMusicUsecaseImpl.execute(music.id!!, request)

        then(musicRepository).should().save(check {
            assertAll(
                { assertEquals(request.title, it.title) },
                { assertEquals(request.singer, it.singer) },
                { assertEquals(request.youtubeUrl, it.youtubeUrl) },
                { assertEquals(thumbnailUrl, it.thumbnailUrl) },
                { assertEquals(request.description, it.description) }
            )
        })
    }

    @Test
    @DisplayName("저장되지 않는 노래를 요청하면 예외가 발생한다")
    fun 저장되지_않는_노래를_요청하면_예외가_발생한다() {
        val notExistMusicId = UUID.randomUUID()
        val request = UpdateMusicRequest(
            title = "newTestMusic",
            singer = "testSinger",
            youtubeUrl = "https://youtuber.com/new",
            description = "newTestMusic description"
        )

        given(musicRepository.findById(notExistMusicId)).willReturn(null)

        val throws = assertThrows<CustomException> {
            updateMusicUsecaseImpl.execute(notExistMusicId, request)
        }

        assertEquals(CustomErrorCode.NOT_FOUND_MUSIC, throws.customErrorCode)
    }
}