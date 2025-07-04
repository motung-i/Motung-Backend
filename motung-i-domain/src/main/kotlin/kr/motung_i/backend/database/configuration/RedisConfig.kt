package kr.motung_i.backend.database.configuration

import io.lettuce.core.SocketOptions
import io.lettuce.core.cluster.ClusterClientOptions
import io.lettuce.core.cluster.ClusterTopologyRefreshOptions
import kr.motung_i.backend.database.properties.ClusterRedisProperties
import kr.motung_i.backend.database.properties.StandaloneRedisProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.data.redis.connection.*
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.serializer.StringRedisSerializer
import java.time.Duration

@Configuration
class RedisConfig(
    private val standaloneRedisProperties: StandaloneRedisProperties,
    private val clusterRedisProperties: ClusterRedisProperties,
) {
    //@Bean
    //@Profile("prod")
    fun prodRedisConnectionFactory(): LettuceConnectionFactory {
        val redisNodes =
            clusterRedisProperties.nodes.map {
                val (host, port) = it.split(":")
                RedisNode(host, port.toInt())
            }

        val clusterConfig =
            RedisClusterConfiguration().apply {
                setClusterNodes(redisNodes)
                maxRedirects = clusterRedisProperties.maxRedirects
            }

        val socketOptions =
            SocketOptions
                .builder()
                .connectTimeout(Duration.ofMillis(100))
                .keepAlive(true)
                .build()

        val topologyRefreshOptions =
            ClusterTopologyRefreshOptions
                .builder()
                .dynamicRefreshSources(true)
                .enableAllAdaptiveRefreshTriggers()
                .enablePeriodicRefresh(Duration.ofMinutes(60))
                .build()

        val clientOptions =
            ClusterClientOptions
                .builder()
                .topologyRefreshOptions(topologyRefreshOptions)
                .socketOptions(socketOptions)
                .build()

        val clientConfig =
            LettuceClientConfiguration
                .builder()
                .clientOptions(clientOptions)
                .commandTimeout(Duration.ofMillis(3000))
                .build()

        return LettuceConnectionFactory(clusterConfig, clientConfig)
    }

    @Bean
    //@Profile("dev")
    fun devRedisConnectionFactory(): LettuceConnectionFactory {
        val standaloneConfig =
            RedisStandaloneConfiguration().apply {
                hostName = standaloneRedisProperties.host
                port = standaloneRedisProperties.port
            }

        val clientConfig =
            LettuceClientConfiguration
                .builder()
                .commandTimeout(Duration.ofMillis(3000))
                .build()

        return LettuceConnectionFactory(standaloneConfig, clientConfig)
    }

    @Bean
    fun redisTemplate(connectionFactory: RedisConnectionFactory): RedisTemplate<String, String> =
        RedisTemplate<String, String>().apply {
            setConnectionFactory(connectionFactory)
            val serializer = StringRedisSerializer()
            keySerializer = serializer
            valueSerializer = serializer
            hashKeySerializer = serializer
            hashValueSerializer = serializer
        }
}
