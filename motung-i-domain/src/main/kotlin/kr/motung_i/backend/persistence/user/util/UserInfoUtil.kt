package kr.motung_i.backend.persistence.user.util

import kr.motung_i.backend.persistence.user.entity.User
import kr.motung_i.backend.persistence.user.entity.enums.Provider
import kr.motung_i.backend.persistence.user.entity.enums.Role

abstract class UserInfoUtil {
    companion object {
        fun getInfoByProvider(
            provider: Provider,
            attribute: Map<String, Any>,
        ): User =
            when (provider) {
                Provider.APPLE ->
                    User(
                        name =
                        attribute["name"] as? String ?: "모툴",
                        role = Role.ROLE_USER,
                        email = attribute["email"].toString(),
                        oauthId = attribute["sub"].toString(),
                        provider = Provider.APPLE,
                    )
                Provider.GOOGLE ->
                    User(
                        name = attribute["name"].toString(),
                        role = Role.ROLE_USER,
                        email = attribute["email"].toString(),
                        oauthId = attribute["sub"].toString(),
                        provider = Provider.GOOGLE,
                    )
            }
    }
}
