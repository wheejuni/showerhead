package com.wheejuni.showerhead.domain

import com.wheejuni.showerhead.domain.repositories.cache.CacheableSpreadEventRepository
import com.wheejuni.showerhead.domain.repositories.tid
import com.wheejuni.showerhead.infra.cache.TestCacheConfig
import org.junit.jupiter.api.Assertions.assertDoesNotThrow
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.redis.DataRedisTest
import org.springframework.context.annotation.Import

@Import(TestCacheConfig::class)
@DataRedisTest
internal class CacheableSpreadEventTest {

    @Autowired
    private lateinit var repository: CacheableSpreadEventRepository
    private val testableObject = CacheableSpreadEvent(tid)

    @BeforeEach
    fun `캐시 초기화`() {
        repository.deleteAll()
        repository.save(testableObject)
    }

    @Test
    fun `레포지토리 조회기능 테스트`() {
        //given
        val maybeCacheEvent = repository.findByEventId(tid)

        //then
        assertDoesNotThrow { maybeCacheEvent!!.receivedUserIds }
    }
}