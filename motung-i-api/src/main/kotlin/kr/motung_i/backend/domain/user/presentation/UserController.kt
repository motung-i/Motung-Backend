package kr.motung_i.backend.domain.user.presentation

import jakarta.validation.Valid
import kr.motung_i.backend.domain.user.presentation.dto.request.UpdateNicknameRequest
import kr.motung_i.backend.domain.user.presentation.dto.response.FetchUserInfoResponse
import kr.motung_i.backend.domain.user.usecase.FetchUserInfoUsecase
import kr.motung_i.backend.domain.user.usecase.RemoveUserUsecase
import kr.motung_i.backend.domain.user.usecase.UpdateNicknameUsecase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/user")
class UserController(
    private val fetchUserInfoUsecase: FetchUserInfoUsecase,
    private val updateNicknameUsecase: UpdateNicknameUsecase,
    private val removeUserUsecase: RemoveUserUsecase,
) {
    @GetMapping
    fun fetchUserInfo(): ResponseEntity<FetchUserInfoResponse> =
        fetchUserInfoUsecase.execute().run {
            ResponseEntity.ok(this)
        }

    @PatchMapping("/nickname")
    fun updateNickname(@Valid @RequestBody request: UpdateNicknameRequest): ResponseEntity<Unit> =
        updateNicknameUsecase.execute(request).run {
            ResponseEntity.noContent().build()
        }

    @DeleteMapping
    fun removeUser(): ResponseEntity<Unit> =
        removeUserUsecase.execute().run {
            ResponseEntity.noContent().build()
        }
}