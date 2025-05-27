package kr.motung_i.backend.database.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties("spring.data.redis.cluster")
class ClusterRedisProperties {
    var nodes: List<String> = emptyList()
    var maxRedirects: Int = 3
}
