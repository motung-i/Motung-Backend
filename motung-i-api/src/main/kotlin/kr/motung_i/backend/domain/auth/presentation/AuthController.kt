package kr.motung_i.backend.domain.auth.presentation

import kr.motung_i.backend.domain.auth.presentation.dto.response.impl.RegisterRequest
import kr.motung_i.backend.domain.auth.presentation.dto.response.impl.TokenRequest
import kr.motung_i.backend.domain.auth.service.AuthService
import kr.motung_i.backend.domain.item.presentation.dto.response.TokenResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
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
        @RequestBody request: TokenRequest,
    ): ResponseEntity<TokenResponse> = ResponseEntity.ok(authService.reissueToken(resolveRefreshToken = request.refreshToken))

    @PostMapping("logout")
    fun logOut(
        @RequestBody request: TokenRequest,
    ): ResponseEntity<Unit> = ResponseEntity.ok(authService.logout(refreshToken = request.refreshToken))

    @GetMapping("check-register")
    fun isUserRegister(): ResponseEntity<Boolean> = ResponseEntity.ok(authService.isUserRegister())

    @PostMapping("register")
    fun register(
        @RequestBody request: RegisterRequest,
    ): ResponseEntity<Unit> = ResponseEntity.ok(authService.register(name = request.name))
}
