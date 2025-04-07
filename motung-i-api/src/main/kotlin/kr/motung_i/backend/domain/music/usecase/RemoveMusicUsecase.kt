package kr.motung_i.backend.domain.music.usecase

import kr.motung_i.backend.global.exception.CustomException
import kr.motung_i.backend.global.exception.enums.CustomErrorCode
import kr.motung_i.backend.persistence.music.entity.enums.MusicStatus
import kr.motung_i.backend.persistence.music.repository.MusicRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
@Transactional
class RemoveMusicUsecase(
    val musicRepository: MusicRepository,
) {
    fun execute(musicId: UUID) {
        val requestMusic = musicRepository.findById(musicId)
            ?: throw CustomException(CustomErrorCode.NOT_FOUND_MUSIC)

        if (requestMusic.musicStatus == MusicStatus.APPROVED) {
            throw CustomException(CustomErrorCode.CANNOT_MODIFY_APPROVED_MUSIC)
        }

        musicRepository.delete(requestMusic)
    }
}