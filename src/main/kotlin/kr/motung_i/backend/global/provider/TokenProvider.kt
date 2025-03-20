package kr.motung_i.backend.global.provider

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import java.util.Date
import javax.crypto.SecretKey

class TokenProvider(
    @Value("JWT_KEY")
    val SECRET_KEY: String,
    val EXPIRE_TIME: Long = 1000 * 60 * 60 * 2L,
) {
    private fun getSecretKey(): SecretKey {
        val keyBytes: ByteArray = Decoders.BASE64.decode(this.SECRET_KEY)
        return Keys.hmacShaKeyFor(keyBytes)
    }

    fun generateToken(
        name: String,
        email: String?,
        role: String,
    ): String {
        val claims: Claims = Jwts.claims().build()
        claims["name"] = name
        claims["email"] = email
        claims["role"] = role

        return Jwts
            .builder()
            .claims(claims)
            .issuedAt(Date(System.currentTimeMillis()))
            .expiration(Date(System.currentTimeMillis() + EXPIRE_TIME))
            .signWith(getSecretKey())
            .compact()
    }

    fun validateToken(token: String): Boolean {
        try {
            val parseToken =
                Jwts
                    .parser()
                    .verifyWith(getSecretKey())
                    .build()
                    .parseSignedClaims(token)
                    .payload
            return !parseToken.issuedAt.after(Date(System.currentTimeMillis()))
        } catch (e: Exception) {
            return false
        }
    }

//    fun tokenResolver(token: String?): String {
//    }
//    fun getEmail(token: String)
}
