package kr.motung_i.backend.domain.admin.persentation

import kr.motung_i.backend.domain.admin.usecase.FetchPendingMusicUsecase
import kr.motung_i.backend.domain.music.presentation.dto.response.MusicListResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/admin")
class AdminController(
    val fetchPendingMusicUsecase: FetchPendingMusicUsecase,
) {
    @GetMapping("/music")
    fun fetchPendingMusicUsecase(): ResponseEntity<MusicListResponse> =
        fetchPendingMusicUsecase.execute().run {
            ResponseEntity.ok(this)
        }
}