package kr.motung_i.backend.domain.music.usecase.impl

import kr.motung_i.backend.domain.music.presentation.dto.response.MusicListResponse
import kr.motung_i.backend.domain.music.usecase.FetchApprovedMusicUsecase
import kr.motung_i.backend.persistence.music.entity.enums.MusicStatus.*
import kr.motung_i.backend.persistence.music.repository.MusicRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class FetchApprovedMusicUsecaseImpl(
    val musicRepository: MusicRepository,
): FetchApprovedMusicUsecase {
    override fun execute(): MusicListResponse =
        MusicListResponse.from(
            musicRepository.findByMusicStatusOrderByRankNumber(APPROVED)
        )
}

