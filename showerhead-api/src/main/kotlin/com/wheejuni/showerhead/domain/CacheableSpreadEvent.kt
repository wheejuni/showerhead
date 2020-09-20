package com.wheejuni.showerhead.domain

import com.wheejuni.showerhead.application.SPREAD_EVENT_TIMEOUT_IN_SECONDS
import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash

@RedisHash(value = "spreadevent", timeToLive = SPREAD_EVENT_TIMEOUT_IN_SECONDS)
class CacheableSpreadEvent(@Id val eventId: String) {

    var receivedUserIds: MutableList<String> = mutableListOf()
}