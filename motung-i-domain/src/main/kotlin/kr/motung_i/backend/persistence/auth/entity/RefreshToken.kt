package kr.motung_i.backend.persistence.auth.entity

import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash
import org.springframework.data.redis.core.TimeToLive
import org.springframework.data.redis.core.index.Indexed

@RedisHash("token")
class RefreshToken(
    @Id
    val userId: String,
    @Indexed
    val refreshToken: String,
    @TimeToLive
    val timeToLive: Long,
)
