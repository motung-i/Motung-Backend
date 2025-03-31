package kr.motung_i.backend.domain.music.usecase

import kr.motung_i.backend.domain.music.presentation.dto.response.MusicListResponse
import kr.motung_i.backend.persistence.music.entity.enums.MusicStatus.*
import kr.motung_i.backend.persistence.music.repository.MusicCustomRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class FetchApprovedMusicUsecase(
    val musicCustomRepository: MusicCustomRepository,
) {
    fun execute(): MusicListResponse =
        MusicListResponse.from(
            musicCustomRepository.findByMusicStatusOrderByRankNumber(APPROVED)
        )
}

