package kr.motung_i.backend.domain.music.usecase.impl

import kr.motung_i.backend.domain.music.presentation.dto.request.CreateMusicRequest
import kr.motung_i.backend.domain.user.usecase.FetchCurrentUserUsecase
import kr.motung_i.backend.global.third_party.youtube.YoutubeAdapter
import kr.motung_i.backend.persistence.music.repository.MusicRepository
import kr.motung_i.backend.testfixture.MusicFixture.createTestVideo
import kr.motung_i.backend.testfixture.UserFixture.createTestUser
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.any
import org.mockito.kotlin.check
import org.mockito.kotlin.given
import org.mockito.kotlin.then

@ExtendWith(MockitoExtension::class)
class CreateMusicUsecaseImplTest {
    @Mock
    private lateinit var youtubeAdapter: YoutubeAdapter

    @Mock
    private lateinit var musicRepository: MusicRepository

    @Mock
    private lateinit var fetchCurrentUserUsecase: FetchCurrentUserUsecase

    @InjectMocks
    private lateinit var createMusicUsecaseImpl: CreateMusicUsecaseImpl

    @Test
    @DisplayName("노래를 저장하면 올바른 데이터로 저장한다")
    fun 노래를_저장하면_올바른_데이터로_저장한다() {
        val request = CreateMusicRequest(
            title = "testMusic",
            singer = "testSinger",
            youtubeUrl = "https://youtube.com",
            description = "testItem description"
        )
        val user = createTestUser()
        val expectedUserId = user.id
        val testVideo = createTestVideo()
        val thumbnailUrl = testVideo.items.first().snippet.thumbnails.medium.url

        given(youtubeAdapter.fetchVideo(any<String>())).willReturn(testVideo)
        given(fetchCurrentUserUsecase.execute()).willReturn(user)

        createMusicUsecaseImpl.execute(request)

        then(musicRepository).should().save(check {
            assertAll(
                { assertEquals(thumbnailUrl, it.thumbnailUrl) },
                { assertEquals(request.title, it.title) },
                { assertEquals(request.singer, it.singer) },
                { assertEquals(request.youtubeUrl, it.youtubeUrl) },
                { assertEquals(request.description, it.description) },
                { assertEquals(expectedUserId, it.user.id) },
            )
        })
    }
}