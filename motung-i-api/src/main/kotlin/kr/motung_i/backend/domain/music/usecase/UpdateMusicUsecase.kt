package kr.motung_i.backend.domain.music.usecase

import kr.motung_i.backend.domain.music.presentation.dto.request.UpdateMusicRequest
import java.util.UUID

interface UpdateMusicUsecase {
    fun execute(musicId: UUID, request: UpdateMusicRequest)
}