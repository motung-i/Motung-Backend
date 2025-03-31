package kr.motung_i.backend.domain.music.presentation

import kr.motung_i.backend.domain.music.presentation.dto.request.CreateMusicRequest
import kr.motung_i.backend.domain.music.usecase.CreateMusicUsecase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("music")
class MusicController(
    private val createMusicUsecase: CreateMusicUsecase,
) {

    @PostMapping
    fun createMusic(@RequestBody createMusicRequest: CreateMusicRequest): ResponseEntity<Void> =
        createMusicUsecase.execute(createMusicRequest).run {
            ResponseEntity.noContent().build()
        }
}