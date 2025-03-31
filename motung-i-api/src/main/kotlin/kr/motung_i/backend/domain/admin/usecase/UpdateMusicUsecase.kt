package kr.motung_i.backend.domain.admin.usecase

import kr.motung_i.backend.domain.admin.persentation.dto.request.UpdateMusicRequest
import kr.motung_i.backend.global.exception.CustomException
import kr.motung_i.backend.global.exception.enums.CustomErrorCode
import kr.motung_i.backend.persistence.music.repository.MusicCustomRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
@Transactional
class UpdateMusicUsecase(
    private val musicCustomRepository: MusicCustomRepository,
) {
    fun execute(musicId: UUID, updateMusicRequest: UpdateMusicRequest) {
        val savedMusic = musicCustomRepository.findById(musicId)
            ?: throw CustomException(CustomErrorCode.NOT_FOUND_MUSIC)
        musicCustomRepository.save(
            savedMusic.update(
                updateMusicRequest.title,
                updateMusicRequest.singer,
                updateMusicRequest.description
            )
        )
    }
}