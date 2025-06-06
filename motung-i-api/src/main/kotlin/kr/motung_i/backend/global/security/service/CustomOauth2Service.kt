package kr.motung_i.backend.global.security.service

import kr.motung_i.backend.domain.user.usecase.CreateUserUsecase
import kr.motung_i.backend.persistence.user.entity.User
import kr.motung_i.backend.persistence.user.entity.enums.Provider
import kr.motung_i.backend.persistence.user.repository.UserRepository
import kr.motung_i.backend.persistence.user.util.UserInfoUtil
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.stereotype.Service

@Service
class CustomOauth2Service(
    private val userRepository: UserRepository,
    private val createUserUsecase: CreateUserUsecase,
) : DefaultOAuth2UserService() {
    override fun loadUser(userRequest: OAuth2UserRequest): OAuth2User {
        val userAttribute: Map<String, Any> = super.loadUser(userRequest).attributes
        val provider: String = userRequest.clientRegistration.registrationId
        val getEntity: User =
            UserInfoUtil.getInfoByProvider(
                attribute = userAttribute,
                provider = Provider.valueOf(provider.uppercase()),
            )

        if (!userRepository.existsByOauthIdAndProvider(getEntity.oauthId, getEntity.provider)) {
            createUserUsecase.execute(getEntity)
        }

        return super.loadUser(userRequest)
    }
}
