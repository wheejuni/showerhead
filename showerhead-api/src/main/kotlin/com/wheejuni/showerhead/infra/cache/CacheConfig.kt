package com.wheejuni.showerhead.infra.cache

import com.wheejuni.showerhead.domain.CacheableSpreadEvent
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.StringRedisSerializer

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
}