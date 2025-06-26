package kr.motung_i.backend.domain.music.usecase

import kr.motung_i.backend.domain.music.presentation.dto.request.RemoveMusicRequest
import java.util.UUID

interface RemoveMusicUsecase {
    fun execute(musicId: UUID, request: RemoveMusicRequest)
}