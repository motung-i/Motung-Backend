package kr.motung_i.backend.domain.music.usecase

import kr.motung_i.backend.domain.music.presentation.dto.request.CreateMusicRequest
import kr.motung_i.backend.domain.user.usecase.FetchCurrentUserUsecase
import kr.motung_i.backend.persistence.music.entity.Music
import kr.motung_i.backend.persistence.music.repository.MusicRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class CreateMusicUsecase(
    private val musicRepository: MusicRepository,
    private val fetchCurrentUserUsecase: FetchCurrentUserUsecase
) {

    fun execute(request: CreateMusicRequest) {
        musicRepository.save(Music(
            user = fetchCurrentUserUsecase.execute(),
            title = request.title,
            description = request.description,
            singer = request.singer,
        ))
    }
}