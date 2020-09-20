package com.wheejuni.showerhead.domain.repositories

import com.wheejuni.showerhead.domain.SpreadEvent
import org.springframework.data.jpa.repository.JpaRepository

interface SpreadEventRepository: JpaRepository<SpreadEvent, Long> {

    fun findByTransactionId(transactionId: String): SpreadEvent?
    fun findByTransacionIdAndRoomId(transactionId: String, roomId: String): SpreadEvent?
    fun findByTransactionIdAndGeneratorId(transactionId: String, generatorId: String): SpreadEvent?
}