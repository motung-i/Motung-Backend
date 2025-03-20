package kr.motung_i.backend.global.service

import kr.motung_i.backend.domain.user.entity.User
import kr.motung_i.backend.domain.user.repository.UserRepository
import kr.motung_i.backend.domain.user.type.OAuth2UserInfo
import kr.motung_i.backend.domain.user.type.Providers
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.stereotype.Service

@Service
class CustomOauth2Service(
    private val oAuth2UserInfo: OAuth2UserInfo,
    private val userRepository: UserRepository,
) : DefaultOAuth2UserService() {
    override fun loadUser(userRequest: OAuth2UserRequest): OAuth2User {
        val userAttribute: Map<String, Any> = super.loadUser(userRequest).attributes
        val provider: String = userRequest.clientRegistration.registrationId
        val getEntity: User =
            oAuth2UserInfo.getInfoByProvider(
                attribute = userAttribute,
                providers = Providers.valueOf(provider.uppercase()),
            )
<<<<<<< HEAD
        if (!userRepository.findByOauthId(getEntity.oauthId).isPresent) {
            userRepository.save(getEntity)
        }

=======
        if (!userRepository.findByEmail(getEntity.email).isPresent) {
            userRepository.save(getEntity)
        }
>>>>>>> origin/feature/#1-create-oauth2-login
        return super.loadUser(userRequest)
    }
}
