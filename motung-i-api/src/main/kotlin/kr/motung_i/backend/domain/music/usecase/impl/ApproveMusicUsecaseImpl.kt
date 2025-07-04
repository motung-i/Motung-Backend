package kr.motung_i.backend.domain.music.usecase.impl

import kr.motung_i.backend.domain.music.presentation.dto.request.ApproveMusicRequest
import kr.motung_i.backend.domain.music.usecase.ApproveMusicUsecase
import kr.motung_i.backend.global.exception.CustomException
import kr.motung_i.backend.global.exception.enums.CustomErrorCode
import kr.motung_i.backend.persistence.music.repository.MusicRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
@Transactional
class ApproveMusicUsecaseImpl(
    private val musicRepository: MusicRepository,
): ApproveMusicUsecase {
    override fun execute(musicId: UUID, request: ApproveMusicRequest) {
        val rankMusic = musicRepository.findByRankNumber(request.rankNumber)
        val requestMusic = musicRepository.findById(musicId)
            ?: throw CustomException(CustomErrorCode.NOT_FOUND_MUSIC)

        if (rankMusic == requestMusic) {
            throw CustomException(CustomErrorCode.MUSIC_ALREADY_APPROVED)
        }

        rankMusic?.cancelMusic()
        requestMusic.approveMusic(request.rankNumber)
    }
}