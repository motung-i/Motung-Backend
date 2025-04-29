package kr.motung_i.backend.domain.music.presentation

import jakarta.validation.Valid
import kr.motung_i.backend.domain.music.presentation.dto.request.ApproveMusicRequest
import kr.motung_i.backend.domain.music.presentation.dto.request.RemoveMusicRequest
import kr.motung_i.backend.domain.music.presentation.dto.request.UpdateMusicRequest
import kr.motung_i.backend.domain.music.usecase.ApproveMusicUsecase
import kr.motung_i.backend.domain.music.usecase.FetchPendingMusicUsecase
import kr.motung_i.backend.domain.music.usecase.UpdateMusicUsecase
import kr.motung_i.backend.domain.music.presentation.dto.response.MusicListResponse
import kr.motung_i.backend.domain.music.usecase.RemoveMusicUsecase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("admin/music")
class AdminMusicController(
    val fetchPendingMusicUsecase: FetchPendingMusicUsecase,
    val updateMusicUsecase: UpdateMusicUsecase,
    val approveMusicUsecase: ApproveMusicUsecase,
    val removeMusicUsecase: RemoveMusicUsecase,
) {
    @GetMapping
    fun fetchPendingMusicList(): ResponseEntity<MusicListResponse> =
        fetchPendingMusicUsecase.execute().run {
            ResponseEntity.ok(this)
        }

    @PatchMapping("{musicId}")
    fun updateMusic(
        @PathVariable musicId: UUID,
        @RequestBody request: UpdateMusicRequest,
    ): ResponseEntity<Unit> =
        updateMusicUsecase.execute(musicId, request).run {
            ResponseEntity.noContent().build()
        }

    @PostMapping("{musicId}")
    fun approveMusic(
        @PathVariable musicId: UUID,
        @Valid @RequestBody request: ApproveMusicRequest,
    ): ResponseEntity<Unit> =
        approveMusicUsecase.execute(musicId, request).run {
            ResponseEntity.noContent().build()
        }

    @DeleteMapping("{musicId}")
    fun removeMusic(
        @PathVariable musicId: UUID,
        @RequestBody request: RemoveMusicRequest,
    ): ResponseEntity<Unit> =
        removeMusicUsecase.execute(musicId, request).run {
            ResponseEntity.noContent().build()
        }
}