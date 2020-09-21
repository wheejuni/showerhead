package com.wheejuni.showerhead.infra.cache

import com.wheejuni.showerhead.domain.CacheableSpreadEvent
import org.redisson.Redisson
import org.redisson.api.RedissonClient
import org.redisson.config.Config
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.ClassPathResource
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.core.RedisKeyValueAdapter
import org.springframework.data.redis.core.RedisKeyValueTemplate
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.StringRedisSerializer

const val REDISSON_CLIENT_CONFIG_DIR = "redisson-config.yml"

@EnableRedisRepositories(basePackages = ["com.wheejuni.showerhead.domain.repositories.cache"])
@Configuration
class CacheConfig(
        private val connectionFactory: RedisConnectionFactory) {

    @Bean
    fun getRedisTemplate(): RedisTemplate<String, CacheableSpreadEvent> {
        val redisTemplate = RedisTemplate<String, CacheableSpreadEvent>()

        redisTemplate.setConnectionFactory(connectionFactory)
        redisTemplate.keySerializer = StringRedisSerializer()
        redisTemplate.valueSerializer = Jackson2JsonRedisSerializer(CacheableSpreadEvent::class.java)

        return redisTemplate
    }

    @Bean
    fun getRedissonClient(): RedissonClient {
        val config = Config.fromYAML(ClassPathResource(REDISSON_CLIENT_CONFIG_DIR).inputStream)
        return Redisson.create(config)
    }
}