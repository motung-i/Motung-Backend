package kr.motung_i.backend.database.properties

import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.validation.annotation.Validated

@Validated
@ConfigurationProperties("spring.data.redis")
class RedisProperties(
    @Size(min = 3)
    val nodes: List<String>,

    @NotNull
    val password: String,

    @NotNull
    val maxRedirects: Int,
)