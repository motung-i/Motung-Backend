package kr.motung_i.backend.domain.auth.presentation

import kr.motung_i.backend.domain.auth.service.AuthService
import kr.motung_i.backend.domain.item.presentation.dto.response.TokenResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/auth")
class AuthController(
    val authService: AuthService,
) {
    @PostMapping("refresh")
    fun refreshToken(
        @RequestBody refreshToken: String,
    ): ResponseEntity<TokenResponse> = ResponseEntity.ok(authService.reissueToken(resolveRefreshToken = refreshToken))
}
