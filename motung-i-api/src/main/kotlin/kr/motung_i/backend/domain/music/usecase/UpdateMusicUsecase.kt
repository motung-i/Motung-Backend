package kr.motung_i.backend.domain.music.usecase

import kr.motung_i.backend.domain.music.presentation.dto.request.UpdateMusicRequest
import kr.motung_i.backend.global.exception.CustomException
import kr.motung_i.backend.global.exception.enums.CustomErrorCode
import kr.motung_i.backend.persistence.music.repository.MusicRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
@Transactional
class UpdateMusicUsecase(
    private val musicRepository: MusicRepository,
) {
    fun execute(musicId: UUID, request: UpdateMusicRequest) {
        val savedMusic = musicRepository.findById(musicId)
            ?: throw CustomException(CustomErrorCode.NOT_FOUND_MUSIC)
        musicRepository.save(
            savedMusic.update(
                request.title,
                request.singer,
                request.description
            )
        )
    }
}