package kr.motung_i.backend.domain.user.type

import kr.motung_i.backend.domain.user.entity.User
import org.springframework.stereotype.Component

@Component
class OAuth2UserInfo {
    fun getInfoByProvider(
        providers: Providers,
        attribute: Map<String, Any>,
    ): User =
        when (providers) {
            Providers.APPLE ->
                User(
                    name =
                        attribute["name"] as? String ?: "모툴",
                    roles = Roles.USER,
                    email = attribute["email"].toString(),
                    oauthId = attribute["sub"].toString(),
                    provider = Providers.APPLE,
                )
            Providers.GOOGLE ->
                User(
                    name = attribute["name"].toString(),
                    roles = Roles.USER,
                    email = attribute["email"].toString(),
                    oauthId = attribute["sub"].toString(),
                    provider = Providers.GOOGLE,
                )
        }
}
