package kr.motung_i.backend.domain.music.usecase

import kr.motung_i.backend.domain.music.presentation.dto.response.MusicListResponse

interface FetchPendingMusicUsecase {
    fun execute(): MusicListResponse
}