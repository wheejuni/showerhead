package com.wheejuni.showerhead.application

import com.wheejuni.showerhead.domain.CacheableSpreadEvent
import com.wheejuni.showerhead.domain.SpreadEvent
import com.wheejuni.showerhead.domain.repositories.cache.CacheableSpreadEventRepository
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Component
import java.time.Duration

const val SPREAD_EVENT_TIMEOUT_IN_SECONDS = 36000L

@Component
class SpreadEventValidator(
        private val template: RedisTemplate<String, CacheableSpreadEvent>,
        private val cacheRepository: CacheableSpreadEventRepository) {

    fun cacheNewEvent(event: CacheableSpreadEvent) {
        cacheRepository.save(event)
    }

    fun addOnReceiversList(transactionId: String, receiverId: String): CacheableSpreadEvent {
        val maybeStoredEvent = cacheRepository.findById(transactionId)

        if(!maybeStoredEvent.isPresent) {
            throw IllegalArgumentException("뿌리기 ID에 매치되는 뿌리기 요청 없음")
        }

        val existingEvent = maybeStoredEvent.get()
        existingEvent.receivedUserIds.add(receiverId)

        return cacheRepository.save(existingEvent)
    }

    fun isValidRequest(transactionId: String): Boolean {
        return cacheRepository.findById(transactionId).isPresent
    }
}