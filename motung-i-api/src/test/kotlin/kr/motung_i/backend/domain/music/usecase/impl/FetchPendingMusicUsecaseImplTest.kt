package kr.motung_i.backend.domain.music.usecase.impl

import kr.motung_i.backend.persistence.music.entity.enums.MusicStatus
import kr.motung_i.backend.persistence.music.repository.MusicRepository
import kr.motung_i.backend.testfixture.MusicFixture.createTestMusic
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.any
import org.mockito.kotlin.given

@ExtendWith(MockitoExtension::class)
class FetchPendingMusicUsecaseImplTest {
    @Mock
    private lateinit var musicRepository: MusicRepository

    @InjectMocks
    private lateinit var fetchPendingMusicUsecaseImpl: FetchPendingMusicUsecaseImpl

    @Test
    @DisplayName("노래를 조회하면 올바른 DTO 형식으로 반환한다")
    fun 노래를_조회하면_올바른_DTO_형식으로_반환한다() {
        val musicList = listOf(createTestMusic(), createTestMusic())
        given(musicRepository.findByMusicStatusOrderByRankNumber(any<MusicStatus>()))
            .willReturn(musicList)

        val result = fetchPendingMusicUsecaseImpl.execute()

        musicList.zip(result.musicList).forEach { (music, dto) ->
            assertAll(
                { assertEquals(music.title, dto.title) },
                { assertEquals(music.description, dto.description) },
                { assertEquals(music.singer, dto.singer) },
                { assertEquals(music.youtubeUrl, dto.youtubeUrl) },
                { assertEquals(music.thumbnailUrl, dto.thumbnailUrl) },
            )
        }
    }
}