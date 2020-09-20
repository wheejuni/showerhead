package com.wheejuni.showerhead.domain

import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash

@RedisHash("spreadevent")
class CacheableSpreadEvent(@Id private val eventId: String) {

    var receivedUserIds: List<String> = emptyList()
}