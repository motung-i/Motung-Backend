package kr.motung_i.backend.domain.admin.persentation

import kr.motung_i.backend.domain.admin.persentation.dto.request.UpdateMusicRequest
import kr.motung_i.backend.domain.admin.usecase.FetchPendingMusicUsecase
import kr.motung_i.backend.domain.admin.usecase.UpdateMusicUsecase
import kr.motung_i.backend.domain.music.presentation.dto.response.MusicListResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/admin")
class AdminController(
    val fetchPendingMusicUsecase: FetchPendingMusicUsecase,
    val updateMusicUsecase: UpdateMusicUsecase,
) {
    @GetMapping("/music")
    fun fetchPendingMusicUsecase(): ResponseEntity<MusicListResponse> =
        fetchPendingMusicUsecase.execute().run {
            ResponseEntity.ok(this)
        }

    @PatchMapping("music/{musicId}")
    fun updateMusicUsecase(
        @PathVariable musicId: UUID,
        @RequestBody updateMusicRequest: UpdateMusicRequest,
    ): ResponseEntity<Unit> =
        updateMusicUsecase.execute(musicId, updateMusicRequest).run {
            ResponseEntity.noContent().build()
        }
}