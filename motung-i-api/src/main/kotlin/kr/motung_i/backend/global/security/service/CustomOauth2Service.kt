package kr.motung_i.backend.global.security.service

import kr.motung_i.backend.persistence.auth.user.type.OAuth2UserInfoUtil
import kr.motung_i.backend.persistence.user.entity.User
import kr.motung_i.backend.persistence.user.entity.enums.Provider
import kr.motung_i.backend.persistence.user.repository.UserCustomRepository
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.stereotype.Service

@Service
class CustomOauth2Service(
    private val oAuth2UserInfo: OAuth2UserInfoUtil,
    private val userRepository: UserCustomRepository,
) : DefaultOAuth2UserService() {
    override fun loadUser(userRequest: OAuth2UserRequest): OAuth2User {
        val userAttribute: Map<String, Any> = super.loadUser(userRequest).attributes
        val provider: String = userRequest.clientRegistration.registrationId
        val getEntity: User =
            oAuth2UserInfo.getInfoByProvider(
                attribute = userAttribute,
                provider = Provider.valueOf(provider.uppercase()),
            )
        if (!userRepository.findByOauthId(getEntity.oauthId).isPresent) {
            userRepository.save(getEntity)
        }
        return super.loadUser(userRequest)
    }
}
