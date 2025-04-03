package kr.motung_i.backend.domain.music.presentation

import jakarta.validation.Valid
import kr.motung_i.backend.domain.music.presentation.dto.request.CreateMusicRequest
import kr.motung_i.backend.domain.music.presentation.dto.response.MusicListResponse
import kr.motung_i.backend.domain.music.usecase.CreateMusicUsecase
import kr.motung_i.backend.domain.music.usecase.FetchApprovedMusicUsecase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("music")
class MusicController(
    private val createMusicUsecase: CreateMusicUsecase,
    private val fetchApprovedMusicUsecase: FetchApprovedMusicUsecase,
) {

    @PostMapping
    fun createMusic(@Valid @RequestBody createMusicRequest: CreateMusicRequest): ResponseEntity<Void> =
        createMusicUsecase.execute(createMusicRequest).run {
            ResponseEntity.noContent().build()
        }

    @GetMapping
    fun fetchApprovedMusicList(): ResponseEntity<MusicListResponse> =
        fetchApprovedMusicUsecase.execute().run {
            ResponseEntity.ok(this)
        }
}