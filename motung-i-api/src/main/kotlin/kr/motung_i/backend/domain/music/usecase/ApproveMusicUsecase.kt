package kr.motung_i.backend.domain.music.usecase

import kr.motung_i.backend.domain.music.presentation.dto.request.ApproveMusicRequest
import kr.motung_i.backend.global.exception.CustomException
import kr.motung_i.backend.global.exception.enums.CustomErrorCode
import kr.motung_i.backend.persistence.music.repository.MusicRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
@Transactional
class ApproveMusicUsecase(
    private val musicRepository: MusicRepository,
) {
    fun execute(musicId: UUID, approveMusicRequest: ApproveMusicRequest) {
        val rankMusic = musicRepository.findByRankNumber(approveMusicRequest.rankNumber)
        val requestMusic = musicRepository.findById(musicId)
            ?: throw CustomException(CustomErrorCode.NOT_FOUND_MUSIC)

        if (rankMusic == requestMusic) {
            throw CustomException(CustomErrorCode.MUSIC_ALREADY_APPROVED)
        }

        rankMusic?.cancelMusic()
        requestMusic.approveMusic(approveMusicRequest.rankNumber)
    }
}