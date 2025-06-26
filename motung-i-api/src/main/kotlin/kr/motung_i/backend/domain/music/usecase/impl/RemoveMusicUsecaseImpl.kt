package kr.motung_i.backend.domain.music.usecase.impl

import kr.motung_i.backend.domain.music.presentation.dto.request.RemoveMusicRequest
import kr.motung_i.backend.domain.music.usecase.RemoveMusicUsecase
import kr.motung_i.backend.domain.user.usecase.SuspensionUserUsecase
import kr.motung_i.backend.global.exception.CustomException
import kr.motung_i.backend.global.exception.enums.CustomErrorCode
import kr.motung_i.backend.persistence.music.entity.enums.MusicStatus
import kr.motung_i.backend.persistence.music.repository.MusicRepository
import kr.motung_i.backend.persistence.user_suspension.entity.SuspensionTarget
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
@Transactional
class RemoveMusicUsecaseImpl(
    val musicRepository: MusicRepository,
    val suspensionUserUsecase: SuspensionUserUsecase,
): RemoveMusicUsecase {
    override fun execute(musicId: UUID, request: RemoveMusicRequest) {
        val requestMusic = musicRepository.findById(musicId)
            ?: throw CustomException(CustomErrorCode.NOT_FOUND_MUSIC)

        if (requestMusic.musicStatus == MusicStatus.APPROVED) {
            throw CustomException(CustomErrorCode.CANNOT_MODIFY_APPROVED_MUSIC)
        }

        suspensionUserUsecase.execute(
            user = requestMusic.user,
            target = SuspensionTarget.MUSIC,
            reasons = setOf(),
            suspensionPeriod = request.suspensionPeriod
        )

        musicRepository.delete(requestMusic)
    }
}