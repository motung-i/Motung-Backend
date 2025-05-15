package kr.motung_i.backend.domain.auth.presentation

import jakarta.validation.Valid
import kr.motung_i.backend.domain.auth.presentation.dto.response.impl.CheckRegisterResponse
import kr.motung_i.backend.domain.auth.presentation.dto.response.impl.RegisterRequest
import kr.motung_i.backend.domain.auth.presentation.dto.response.impl.TokenRequest
import kr.motung_i.backend.domain.auth.usecase.CheckIsUserRegisterUsecase
import kr.motung_i.backend.domain.auth.usecase.LogoutUsecase
import kr.motung_i.backend.domain.auth.usecase.RegisterUsecase
import kr.motung_i.backend.domain.auth.usecase.ReissueTokenUsecase
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
}
