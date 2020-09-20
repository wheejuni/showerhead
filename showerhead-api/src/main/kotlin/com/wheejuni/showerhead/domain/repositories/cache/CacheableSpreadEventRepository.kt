package com.wheejuni.showerhead.domain.repositories.cache

import com.wheejuni.showerhead.domain.CacheableSpreadEvent
import org.springframework.data.repository.CrudRepository

interface CacheableSpreadEventRepository: CrudRepository<CacheableSpreadEvent, String> {
    fun findByEventId(eventId: String): CacheableSpreadEvent?
}
