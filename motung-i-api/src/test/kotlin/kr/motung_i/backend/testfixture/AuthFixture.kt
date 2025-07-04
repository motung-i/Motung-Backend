package kr.motung_i.backend.testfixture

import kr.motung_i.backend.persistence.auth.entity.RefreshToken
import kr.motung_i.backend.persistence.device_token.entity.DeviceToken
import kr.motung_i.backend.persistence.user.entity.User

object AuthFixture {
    fun createTestDeviceToken(
        user: User,
        deviceToken: String = "DeviceToken"
    ): DeviceToken {
        return DeviceToken(user.id!!, deviceToken)
    }

    fun createTestRefreshToken(
        user: User,
        refreshToken: String = "Bearer TestToken"
    ): RefreshToken {
        return RefreshToken(
            user.id.toString(),
            refreshToken = refreshToken,
            timeToLive = 10000L,
        )
    }
}