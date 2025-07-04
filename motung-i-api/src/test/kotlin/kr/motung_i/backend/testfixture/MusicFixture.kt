package kr.motung_i.backend.testfixture

import kr.motung_i.backend.global.third_party.youtube.dto.FetchVideoResponse
import kr.motung_i.backend.persistence.music.entity.Music
import kr.motung_i.backend.persistence.user.entity.User
import kr.motung_i.backend.testfixture.UserFixture.createTestUser
import java.util.*

object MusicFixture {
    fun createTestMusic(
        user: User = createTestUser(),
        title: String = "testMusic",
        singer: String = "testSinger",
        thumbnailUrl: String = "https://thumbnail",
        youtubeUrl: String = "https://youtube.com",
        description: String = "testMusic description",
        rankNumber: Int? = null,
    ): Music {
        return Music(
            id = UUID.randomUUID(),
            user = user,
            title = title,
            singer = singer,
            thumbnailUrl = thumbnailUrl,
            youtubeUrl = youtubeUrl,
            description = description,
        ).apply {
            rankNumber?.let { this.approveMusic(rankNumber) }
        }
    }

    fun createTestVideo(): FetchVideoResponse {
        return FetchVideoResponse(
            items = listOf(
                FetchVideoResponse.VideoItem(
                    snippet = FetchVideoResponse.Snippet(
                        thumbnails = FetchVideoResponse.Thumbnails(
                            medium = FetchVideoResponse.Thumbnail(
                                url = "https://thumbnail/new"
                            )
                        )
                    )
                )
            )
        )
    }
}