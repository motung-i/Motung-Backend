package kr.motung_i.backend.global.security.provider

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import jakarta.servlet.http.HttpServletRequest
import kr.motung_i.backend.persistence.user.entity.enums.Roles
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.util.Date
import javax.crypto.SecretKey

@Component
class JwtTokenProvider(
    @Value("\${ACCESS_JWT_KEY}")
    private val accessJwtKey: String,
    @Value("\${REFRESH_JWT_KEY}")
    private val refreshJwtKey: String,
    @Value("\${ACCESS_EXPIRE_TIME}")
    private val accessExpireTime: Long,
    @Value("\${REFRESH_EXPIRE_TIME}")
    private val refreshExpireTime: Long,
) {
    private fun getSecretKey(isRefresh: Boolean): SecretKey {
        val key = if (isRefresh) refreshJwtKey else accessJwtKey
        val keyBytes: ByteArray = Decoders.BASE64.decode(key)
        return Keys.hmacShaKeyFor(keyBytes)
    }

    fun generateToken(
        clientId: String,
        role: Roles,
        isRefresh: Boolean,
    ): String {
        val now = System.currentTimeMillis()
        val expirationTime = if (isRefresh) refreshExpireTime else accessExpireTime
        val expirationDate = Date(now + expirationTime)

        val claims: Claims = Jwts.claims().add("role", role).build()

        return Jwts
            .builder()
            .claims(claims)
            .subject(clientId)
            .issuedAt(Date(now))
            .expiration(expirationDate)
            .signWith(getSecretKey(isRefresh))
            .compact()
    }

    fun validateToken(
        token: String,
        isRefresh: Boolean,
    ): Boolean {
        try {
            val parseToken =
                Jwts
                    .parser()
                    .verifyWith(getSecretKey(isRefresh))
                    .build()
                    .parseSignedClaims(token)
                    .payload
            return !parseToken.issuedAt.after(Date(System.currentTimeMillis()))
        } catch (e: Exception) {
            return false
        }
    }

    fun tokenResolveByRequest(request: HttpServletRequest): String? {
        val token = request.getHeader("Authorization")
        return if (token == null || !token.startsWith("Bearer ")) {
            null
        } else {
            token.substring(7)
        }
    }

    fun getClientId(
        token: String,
        isRefresh: Boolean,
    ): String =
        Jwts
            .parser()
            .verifyWith(getSecretKey(isRefresh))
            .build()
            .parseSignedClaims(token)
            .payload.subject

    fun getUserRoles(
        token: String,
        isRefresh: Boolean,
    ): Roles =
        Roles.valueOf(
            Jwts
                .parser()
                .verifyWith(getSecretKey(isRefresh))
                .build()
                .parseSignedClaims(token)
                .payload["role"] as String
        )
}
