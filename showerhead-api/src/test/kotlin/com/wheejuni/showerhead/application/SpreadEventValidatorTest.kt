package com.wheejuni.showerhead.application

import com.wheejuni.showerhead.domain.CacheableSpreadEvent

import com.wheejuni.showerhead.domain.TESTABLE_SPREAD_EVENT
import com.wheejuni.showerhead.domain.repositories.cache.CacheableSpreadEventRepository
import com.wheejuni.showerhead.domain.repositories.tid
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.redis.core.RedisTemplate
import java.util.concurrent.TimeUnit

@SpringBootTest
internal class SpreadEventValidatorTest {

    @Autowired
    private lateinit var validator: SpreadEventValidator

    @Autowired
    private lateinit var template: RedisTemplate<String, CacheableSpreadEvent>

    @Autowired
    private lateinit var cacheRepository: CacheableSpreadEventRepository

    @BeforeEach
    fun `테스트 데이터 저장`() {
        cacheRepository.deleteAll()
        validator.cacheNewEvent(TESTABLE_SPREAD_EVENT)
    }

    @Test
    fun `신규 이벤트 생성 테스트`() {
        //given
        val eventObject = TESTABLE_SPREAD_EVENT

        //when
        validator.cacheNewEvent(eventObject)

        //then
        validator.isValidRequest(tid)
    }
}