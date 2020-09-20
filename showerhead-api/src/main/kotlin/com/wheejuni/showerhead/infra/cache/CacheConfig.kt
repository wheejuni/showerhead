package com.wheejuni.showerhead.infra.cache

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories
import org.springframework.data.redis.serializer.StringRedisSerializer

@EnableRedisRepositories(basePackages = ["com.wheejuni.showerhead.domain.repositories.cache"])
@Configuration
class CacheConfig(
        private val connectionFactory: RedisConnectionFactory) {

    @Bean
    fun getRedisTemplate(): RedisTemplate<String, Object> {
        val redisTemplate = RedisTemplate<String, Object>()

        redisTemplate.setConnectionFactory(connectionFactory)
        redisTemplate.setDefaultSerializer(StringRedisSerializer())

        return redisTemplate
    }
}