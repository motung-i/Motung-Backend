package kr.motung_i.backend.database.properties

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties("spring.data.redis")
class ClusterRedisProperties(
    val nodes: List<String> = emptyList(),
    val maxRedirects: Int = 3,
)
