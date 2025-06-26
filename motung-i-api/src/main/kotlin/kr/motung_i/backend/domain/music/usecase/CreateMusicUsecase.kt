package kr.motung_i.backend.domain.music.usecase

import kr.motung_i.backend.domain.music.presentation.dto.request.CreateMusicRequest

interface CreateMusicUsecase {
    fun execute(request: CreateMusicRequest)
}