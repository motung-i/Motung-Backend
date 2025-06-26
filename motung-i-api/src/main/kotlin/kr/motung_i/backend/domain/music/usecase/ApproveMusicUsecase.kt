package kr.motung_i.backend.domain.music.usecase

import kr.motung_i.backend.domain.music.presentation.dto.request.ApproveMusicRequest
import java.util.UUID

interface ApproveMusicUsecase {
    fun execute(musicId: UUID, request: ApproveMusicRequest)
}