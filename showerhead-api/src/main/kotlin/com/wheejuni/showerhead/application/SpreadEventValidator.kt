package com.wheejuni.showerhead.application

import com.wheejuni.showerhead.domain.CacheableSpreadEvent
import com.wheejuni.showerhead.domain.SpreadEvent
import com.wheejuni.showerhead.domain.repositories.cache.CacheableSpreadEventRepository
import org.springframework.data.redis.core.RedisKeyValueTemplate
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.time.Duration

const val SPREAD_EVENT_TIMEOUT_IN_SECONDS = 600L

@Component
class SpreadEventValidator(
        private val template: RedisTemplate<String, CacheableSpreadEvent>,
        private val cacheRepository: CacheableSpreadEventRepository) {

    fun cacheNewEvent(event: CacheableSpreadEvent) {
        cacheRepository.save(event)
    }

    fun isValidRequest(transactionId: String): Boolean {
        return cacheRepository.findById(transactionId).isPresent
    }
}