package kr.motung_i.backend.database.properties

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties("spring.data.redis")
class StandaloneRedisProperties(
    val host: String,
    val port: Int = 6379,
)
