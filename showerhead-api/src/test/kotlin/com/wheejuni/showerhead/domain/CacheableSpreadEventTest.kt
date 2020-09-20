package com.wheejuni.showerhead.domain

import com.wheejuni.showerhead.domain.repositories.cache.CacheableSpreadEventRepository
import com.wheejuni.showerhead.domain.repositories.tid
import com.wheejuni.showerhead.infra.cache.TestCacheConfig
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.redis.DataRedisTest
import org.springframework.context.annotation.Import

val TESTABLE_SPREAD_EVENT = CacheableSpreadEvent(tid)

@Import(TestCacheConfig::class)
@DataRedisTest
internal class CacheableSpreadEventTest {

    @Autowired
    private lateinit var repository: CacheableSpreadEventRepository

    @BeforeEach
    fun `캐시 초기화`() {
        repository.save(TESTABLE_SPREAD_EVENT)
    }

    @Test
    fun `레포지토리 조회기능 테스트`() {
        //given
        val maybeCacheEvent = repository.findById(tid)

        //then
        assertTrue(maybeCacheEvent.isPresent)
    }
}