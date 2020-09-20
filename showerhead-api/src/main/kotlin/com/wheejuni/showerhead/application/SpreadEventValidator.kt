package com.wheejuni.showerhead.application

import com.wheejuni.showerhead.domain.CacheableSpreadEvent
import com.wheejuni.showerhead.domain.repositories.cache.CacheableSpreadEventRepository
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Component

@Component
class SpreadEventValidator(
        private val template: RedisTemplate<String, CacheableSpreadEvent>,
        private val cacheRepository: CacheableSpreadEventRepository
) {
}