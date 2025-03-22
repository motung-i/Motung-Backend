package kr.motung_i.backend.global.provider

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import jakarta.servlet.http.HttpServletRequest
import kr.motung_i.backend.domain.user.type.Roles
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.util.Date
import javax.crypto.SecretKey

@Component
class TokenProvider {
    @Suppress("ktlint:standard:property-naming")
    @Value("\${ACCESS_JWT_KEY}")
    lateinit var ACCESS_SECRET_KEY: String

    @Suppress("ktlint:standard:property-naming")
    @Value("\${REFRESH_JWT_KEY}")
    lateinit var REFRESH_SECRET_KEY: String

    @Suppress("ktlint:standard:property-naming")
    @Value("\${ACCESS_EXPIRE_TIME}")
    lateinit var ACCESS_EXPIRE_TIME: String

    @Suppress("ktlint:standard:property-naming")
    @Value("\${REFRESH_EXPIRE_TIME}")
    lateinit var REFRESH_EXPIRE_TIME: String

    private fun getSecretKey(isRefresh: Boolean): SecretKey {
        val key = if (isRefresh) REFRESH_SECRET_KEY else ACCESS_SECRET_KEY
        val keyBytes: ByteArray = Decoders.BASE64.decode(key)
        return Keys.hmacShaKeyFor(keyBytes)
    }

    fun generateToken(
        clientId: String,
        role: Roles,
        isRefresh: Boolean,
    ): String {
        val now = System.currentTimeMillis()
        val expirationTime = if (isRefresh) REFRESH_EXPIRE_TIME.toLong() else ACCESS_EXPIRE_TIME.toLong()
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
        Jwts
            .parser()
            .verifyWith(getSecretKey(isRefresh))
            .build()
            .parseSignedClaims(token)
            .payload["roles"] as Roles
}
