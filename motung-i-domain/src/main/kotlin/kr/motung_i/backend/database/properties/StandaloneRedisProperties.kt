package kr.motung_i.backend.database.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties("spring.data.redis")
class StandaloneRedisProperties {
    lateinit var host: String
    var port: Int = 6379
}
