package kr.motung_i.backend.domain.auth.service

import kr.motung_i.backend.domain.item.presentation.dto.response.TokenResponse

interface AuthService {
    fun logout(refreshToken: String)

    fun isUserRegister(): Boolean

    fun register(name: String)

    fun reissueToken(resolveRefreshToken: String): TokenResponse
}
