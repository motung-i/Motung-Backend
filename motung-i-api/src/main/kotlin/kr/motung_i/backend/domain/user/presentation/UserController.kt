package kr.motung_i.backend.domain.user.presentation

import jakarta.validation.Valid
import kr.motung_i.backend.domain.user.presentation.dto.request.UpdateNicknameRequest
import kr.motung_i.backend.domain.user.usecase.UpdateNicknameUsecase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/user")
class UserController(
    private val updateNicknameUsecase: UpdateNicknameUsecase,
) {
    @PatchMapping("/nickname")
    fun updateNickname(@Valid @RequestBody request: UpdateNicknameRequest): ResponseEntity<Unit> =
        updateNicknameUsecase.execute(request).run {
            ResponseEntity.noContent().build()
        }
}