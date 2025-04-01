package kr.motung_i.backend.domain.admin.persentation

import jakarta.validation.Valid
import kr.motung_i.backend.domain.admin.persentation.dto.request.ApproveMusicRequest
import kr.motung_i.backend.domain.admin.persentation.dto.request.UpdateMusicRequest
import kr.motung_i.backend.domain.admin.usecase.ApproveMusicUsecase
import kr.motung_i.backend.domain.admin.usecase.FetchPendingMusicUsecase
import kr.motung_i.backend.domain.admin.usecase.UpdateMusicUsecase
import kr.motung_i.backend.domain.music.presentation.dto.response.MusicListResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("admin")
class AdminController(
    val fetchPendingMusicUsecase: FetchPendingMusicUsecase,
    val updateMusicUsecase: UpdateMusicUsecase,
    val approveMusicUsecase: ApproveMusicUsecase,
) {
    /* music */
    @GetMapping("music")
    fun fetchPendingMusic(): ResponseEntity<MusicListResponse> =
        fetchPendingMusicUsecase.execute().run {
            ResponseEntity.ok(this)
        }

    @PatchMapping("music/{musicId}")
    fun updateMusic(
        @PathVariable musicId: UUID,
        @RequestBody updateMusicRequest: UpdateMusicRequest,
    ): ResponseEntity<Unit> =
        updateMusicUsecase.execute(musicId, updateMusicRequest).run {
            ResponseEntity.noContent().build()
        }

    @PostMapping("music/{musicId}")
    fun approveMusic(
        @PathVariable musicId: UUID,
        @Valid @RequestBody approveMusicRequest: ApproveMusicRequest,
    ): ResponseEntity<Unit> =
        approveMusicUsecase.execute(musicId, approveMusicRequest).run {
            ResponseEntity.noContent().build()
        }
}