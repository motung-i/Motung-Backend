package kr.motung_i.backend.database.configuration

import io.lettuce.core.SocketOptions
import io.lettuce.core.cluster.ClusterClientOptions
import io.lettuce.core.cluster.ClusterTopologyRefreshOptions
import kr.motung_i.backend.database.properties.RedisProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisClusterConfiguration
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.connection.RedisNode
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.serializer.StringRedisSerializer
import java.time.Duration

@Configuration
@EnableConfigurationProperties(RedisProperties::class)
class RedisConfig(
    private val redisProperties: RedisProperties,
) {
    @Bean
    fun connectionFactory(): LettuceConnectionFactory {
        val nodes = redisProperties.nodes
        val password = redisProperties.password
        val maxRedirects = redisProperties.maxRedirects
        val redisNodes = nodes.map { node ->
            val (host, port) = node.split(":")
            RedisNode(host, port.toInt())
        }

        val clusterConfiguration = RedisClusterConfiguration()
        clusterConfiguration.setClusterNodes(redisNodes)
        clusterConfiguration.setPassword(password)
        clusterConfiguration.maxRedirects = maxRedirects

        val socketOptions = SocketOptions.builder()
            .connectTimeout(Duration.ofMillis(100))
            .keepAlive(true)
            .build()

        val clusterTopologyRefreshOptions = ClusterTopologyRefreshOptions.builder()
            .dynamicRefreshSources(true)
            .enableAllAdaptiveRefreshTriggers()
            .enablePeriodicRefresh(Duration.ofMinutes(60))
            .build()

        val clientOptions = ClusterClientOptions.builder()
            .topologyRefreshOptions(clusterTopologyRefreshOptions)
            .socketOptions(socketOptions)
            .build()

        val clientConfiguration = LettuceClientConfiguration.builder()
            .clientOptions(clientOptions)
            .commandTimeout(Duration.ofMillis(3000))
            .build()

        return LettuceConnectionFactory(clusterConfiguration, clientConfiguration)
    }

    @Bean
    fun redisTemplate(connectionFactory: RedisConnectionFactory?): RedisTemplate<String, String> {
        val template = RedisTemplate<String, String>()
        template.connectionFactory = connectionFactory

        val serializer = StringRedisSerializer()
        template.keySerializer = serializer
        template.valueSerializer = serializer
        template.hashKeySerializer = serializer
        template.hashValueSerializer = serializer

        return template
    }
}