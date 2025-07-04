package kr.motung_i.backend.global.third_party.apple.service

import com.nimbusds.jose.JWSAlgorithm
import com.nimbusds.jose.JWSHeader
import com.nimbusds.jose.JWSSigner
import com.nimbusds.jose.crypto.ECDSASigner
import com.nimbusds.jose.jwk.JWKSet
import com.nimbusds.jwt.JWTClaimsSet
import com.nimbusds.jwt.SignedJWT
import kr.motung_i.backend.global.exception.CustomException
import kr.motung_i.backend.global.exception.enums.CustomErrorCode
import kr.motung_i.backend.global.third_party.apple.dto.FetchDataFromAppleRequest
import org.bouncycastle.util.io.pem.PemReader
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.*
import org.springframework.stereotype.Service
import org.springframework.util.LinkedMultiValueMap
import org.springframework.web.client.RestTemplate
import java.io.*
import java.security.KeyFactory
import java.security.interfaces.ECPrivateKey
import java.security.spec.PKCS8EncodedKeySpec
import java.util.*

@Service
class AppleService {
    @Value("\${cloud.apple.team-id}")
    private lateinit var appleTeamId: String

    @Value("\${cloud.apple.apple-login-key}")
    private lateinit var appleLoginKey: String

    @Value("\${cloud.apple.client-id}")
    private lateinit var appleClientId: String

    // 키 경로 대신 키 값을 직접 받도록 변경
    @Value("\${cloud.apple.private-key}")
    private lateinit var applePrivateKey: String

    companion object {
        private const val APPLE_AUTH_URL = "https://appleid.apple.com"
        private const val APPLE_KEYS_URL = "https://appleid.apple.com/auth/keys"
    }

    @Throws(Exception::class)
    fun getAppleInfoFromIdToken(idToken: String): FetchDataFromAppleRequest {
        val signedJWT = SignedJWT.parse(idToken)

        if (!verifyIdTokenSignature(signedJWT)) {
            throw CustomException(CustomErrorCode.VALIDATION_ERROR)
        }

        val claimsSet = signedJWT.jwtClaimsSet

        if (claimsSet.expirationTime.before(Date())) {
            throw CustomException(CustomErrorCode.VALIDATION_ERROR)
        }

        if (!claimsSet.audience.contains(appleClientId)) {
            throw CustomException(CustomErrorCode.VALIDATION_ERROR)
        }

        val userId = claimsSet.subject
        val email = claimsSet.getStringClaim("email")

        return FetchDataFromAppleRequest(
            id = userId,
            token = idToken,
            email = email,
        )
    }

    @Throws(Exception::class)
    private fun verifyIdTokenSignature(signedJWT: SignedJWT): Boolean {
        try {
            val restTemplate = RestTemplate()
            val response = restTemplate.getForObject(APPLE_KEYS_URL, String::class.java)
            val jwkSet = JWKSet.parse(response)

            val keyId = signedJWT.header.keyID
            jwkSet.getKeyByKeyId(keyId) ?: return false
            return true
        } catch (e: Exception) {
            return false
        }
    }

    @Throws(Exception::class)
    fun revokeAppleToken(refreshToken: String): Boolean {
        val clientSecret = createClientSecret()
        val restTemplate = RestTemplate()

        val headers =
            HttpHeaders().apply {
                contentType = MediaType.APPLICATION_FORM_URLENCODED
            }

        val params =
            LinkedMultiValueMap<String, String>().apply {
                add("client_id", appleClientId)
                add("client_secret", clientSecret)
                add("token", refreshToken)
                add("token_type_hint", "refresh_token")
            }

        val requestEntity = HttpEntity(params, headers)

        return try {
            val response =
                restTemplate.exchange(
                    "$APPLE_AUTH_URL/auth/revoke",
                    HttpMethod.POST,
                    requestEntity,
                    String::class.java,
                )
            response.statusCode == HttpStatus.OK
        } catch (e: Exception) {
            false
        }
    }

    @Throws(Exception::class)
    private fun createClientSecret(): String {
        val now = Date()
        val header = JWSHeader.Builder(JWSAlgorithm.ES256).keyID(appleLoginKey).build()
        val claimsSet =
            JWTClaimsSet
                .Builder()
                .issuer(appleTeamId)
                .issueTime(now)
                .expirationTime(Date(now.time + 3600000))
                .audience(APPLE_AUTH_URL)
                .subject(appleClientId)
                .build()

        val jwt = SignedJWT(header, claimsSet)
        val keySpec = PKCS8EncodedKeySpec(getPrivateKey())
        val keyFactory = KeyFactory.getInstance("EC")
        val ecPrivateKey = keyFactory.generatePrivate(keySpec) as ECPrivateKey
        val signer: JWSSigner = ECDSASigner(ecPrivateKey)

        jwt.sign(signer)
        return jwt.serialize()
    }

    @Throws(Exception::class)
    private fun getPrivateKey(): ByteArray {
        // 키 값을 직접 파싱
        StringReader(applePrivateKey).use { keyReader ->
            PemReader(keyReader).use { pemReader ->
                val pemObject = pemReader.readPemObject()
                return pemObject.content
            }
        }
    }
}
