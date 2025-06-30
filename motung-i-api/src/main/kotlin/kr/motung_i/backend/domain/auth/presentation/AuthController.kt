package kr.motung_i.backend.domain.auth.presentation

import jakarta.validation.Valid
import kr.motung_i.backend.domain.auth.presentation.dto.request.AppleLoginRequest
import kr.motung_i.backend.domain.auth.presentation.dto.request.GoogleLoginRequest
import kr.motung_i.backend.domain.auth.presentation.dto.response.impl.CheckRegisterResponse
import kr.motung_i.backend.domain.auth.presentation.dto.response.impl.RegisterRequest
import kr.motung_i.backend.domain.auth.presentation.dto.response.impl.TokenRequest
import kr.motung_i.backend.domain.auth.presentation.dto.response.impl.TokenResponseData
import kr.motung_i.backend.domain.auth.usecase.*
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
    private val checkIsUserRegisterUsecase: CheckIsUserRegisterUsecase,
    private val logoutUsecase: LogoutUsecase,
    private val registerUsecase: RegisterUsecase,
    private val reissueTokenUsecase: ReissueTokenUsecase,
    private val googleLoginUsecase: GoogleLoginUsecase,
    private val appleLoginUsecase: AppleLoginUsecase,
) {
    @PostMapping("refresh")
    fun refreshToken(
        @RequestBody request: TokenRequest,
    ): ResponseEntity<TokenResponse> =
        reissueTokenUsecase
            .execute(
                request = request,
            ).run {
                ResponseEntity.ok(this)
            }

    @PostMapping("logout")
    fun logOut(
        @RequestBody request: TokenRequest,
    ): ResponseEntity<Unit> =
        logoutUsecase
            .execute(
                request,
            ).run {
                ResponseEntity.noContent().build()
            }

    @GetMapping("check-register")
    fun isUserRegister(): ResponseEntity<CheckRegisterResponse> =
        checkIsUserRegisterUsecase.execute().run {
            ResponseEntity.ok(this)
        }

    @PostMapping("register")
    fun register(
        @Valid @RequestBody request: RegisterRequest,
    ): ResponseEntity<Unit> =
        registerUsecase
            .execute(
                request = request,
            ).run {
                ResponseEntity.noContent().build()
            }

    @PostMapping("google/login/callback")
    fun googleLogin(
        @RequestBody request: GoogleLoginRequest,
    ): ResponseEntity<TokenResponseData> =
        googleLoginUsecase.execute(request).run {
            ResponseEntity.ok(this)
        }

    @PostMapping("apple/login/callback")
    fun appleLogin(
        @RequestBody request: AppleLoginRequest,
    ): ResponseEntity<TokenResponseData> =
        appleLoginUsecase.execute(request).run {
            ResponseEntity.ok(this)
        }
}
