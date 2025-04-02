package kr.motung_i.backend.global.security.service

import kr.motung_i.backend.persistence.user.entity.User
import kr.motung_i.backend.persistence.user.entity.enums.Provider
import kr.motung_i.backend.persistence.user.repository.UserCustomRepository
import kr.motung_i.backend.persistence.user.util.UserInfoUtil
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.stereotype.Service

@Service
class CustomOauth2Service(
    private val userRepository: UserCustomRepository,
) : DefaultOAuth2UserService() {
    override fun loadUser(userRequest: OAuth2UserRequest): OAuth2User {
        val userAttribute: Map<String, Any> = super.loadUser(userRequest).attributes
        val provider: String = userRequest.clientRegistration.registrationId
        val getEntity: User =
            UserInfoUtil.getInfoByProvider(
                attribute = userAttribute,
                provider = Provider.valueOf(provider.uppercase()),
            )
        userRepository.findByOauthId(getEntity.oauthId) ?: userRepository.save(getEntity)
        return super.loadUser(userRequest)
    }
}
