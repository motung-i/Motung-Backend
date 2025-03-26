package kr.motung_i.backend.persistence.auth.user.type

import kr.motung_i.backend.persistence.user.entity.User
import kr.motung_i.backend.persistence.user.entity.enums.Providers
import kr.motung_i.backend.persistence.user.entity.enums.Roles
import org.springframework.stereotype.Component

@Component
class OAuth2UserInfoUtil {
    fun getInfoByProvider(
        providers: Providers,
        attribute: Map<String, Any>,
    ): User =
        when (providers) {
            Providers.APPLE ->
                User(
                    name =
                        attribute["name"] as? String ?: "모툴",
                    roles = Roles.ROLE_USER,
                    email = attribute["email"].toString(),
                    oauthId = attribute["sub"].toString(),
                    provider = Providers.APPLE,
                )
            Providers.GOOGLE ->
                User(
                    name = attribute["name"].toString(),
                    roles = Roles.ROLE_USER,
                    email = attribute["email"].toString(),
                    oauthId = attribute["sub"].toString(),
                    provider = Providers.GOOGLE,
                )
        }
}
