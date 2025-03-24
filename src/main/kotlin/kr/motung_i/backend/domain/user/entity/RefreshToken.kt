package kr.motung_i.backend.domain.user.entity

import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash
import org.springframework.data.redis.core.TimeToLive

@RedisHash("token")
class RefreshToken(
    @Id
    private val clientId: String,
    private val refreshToken: String,
    @TimeToLive
    private val timeToLive: Long,
)
