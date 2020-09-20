package com.wheejuni.showerhead.infra.cache

import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import redis.embedded.RedisServer

@TestConfiguration
class TestCacheConfig {

    @Bean(initMethod = "start", destroyMethod = "stop")
    fun embeddedRedisServer(): RedisServer {
        return RedisServer(6379)
    }
}