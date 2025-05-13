package kr.motung_i.backend.domain.music.usecase

import kr.motung_i.backend.domain.music.presentation.dto.request.UpdateMusicRequest
import kr.motung_i.backend.global.exception.CustomException
import kr.motung_i.backend.global.exception.enums.CustomErrorCode
import kr.motung_i.backend.global.third_party.youtube.YoutubeAdapter
import kr.motung_i.backend.persistence.music.repository.MusicRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
@Transactional
class UpdateMusicUsecase(
    private val musicRepository: MusicRepository,
    private val youtubeAdapter: YoutubeAdapter,
) {
    fun execute(musicId: UUID, request: UpdateMusicRequest) {
        val savedMusic = musicRepository.findById(musicId)
            ?: throw CustomException(CustomErrorCode.NOT_FOUND_MUSIC)

        val youtubeThumbnailUrl = request.youtubeUrl?.let {
            val video = youtubeAdapter.fetchVideo(it).items.firstOrNull()
                ?: throw CustomException(CustomErrorCode.NOT_FOUND_MUSIC_URL)
            video.snippet.thumbnails.medium.url
        }

        musicRepository.save(
            savedMusic.update(
                title = request.title,
                singer = request.singer,
                thumbnailUrl = youtubeThumbnailUrl,
                youtubeUrl = request.youtubeUrl,
                description = request.description,
            )
        )
    }
}