package kr.motung_i.backend.domain.music.usecase

import kr.motung_i.backend.domain.music.presentation.dto.request.CreateMusicRequest
import kr.motung_i.backend.domain.user.usecase.FetchCurrentUserUsecase
import kr.motung_i.backend.global.exception.CustomException
import kr.motung_i.backend.global.exception.enums.CustomErrorCode
import kr.motung_i.backend.global.third_party.youtube.YoutubeAdapter
import kr.motung_i.backend.persistence.music.entity.Music
import kr.motung_i.backend.persistence.music.repository.MusicRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class CreateMusicUsecase(
    private val youtubeAdapter: YoutubeAdapter,
    private val musicRepository: MusicRepository,
    private val fetchCurrentUserUsecase: FetchCurrentUserUsecase,
) {

    fun execute(request: CreateMusicRequest) {
        val video = youtubeAdapter.fetchVideo(request.youtubeUrl).items.firstOrNull()
            ?: throw CustomException(CustomErrorCode.NOT_FOUND_MUSIC_URL)
        val videoThumbnailUrl = video.snippet.thumbnails.medium.url

        musicRepository.save(Music(
            user = fetchCurrentUserUsecase.execute(),
            title = request.title,
            youtubeUrl = request.youtubeUrl,
            thumbnailUrl = videoThumbnailUrl,
            description = request.description,
            singer = request.singer,
        ))
    }
}