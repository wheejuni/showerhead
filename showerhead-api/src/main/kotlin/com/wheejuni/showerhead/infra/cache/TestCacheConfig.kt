package com.wheejuni.showerhead.infra.cache

import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import redis.embedded.RedisServer

const val TESTABLE_REDIS_PORT = 16379

@TestConfiguration
class TestCacheConfig {

    @Bean(initMethod = "start", destroyMethod = "stop")
    fun embeddedRedisServer(): RedisServer {
        return RedisServer.builder()
                .port(TESTABLE_REDIS_PORT)
                .setting("maxheap 64M")
                .build()
    }
}