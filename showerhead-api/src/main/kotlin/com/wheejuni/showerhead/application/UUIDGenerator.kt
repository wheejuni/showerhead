package com.wheejuni.showerhead.application

import com.wheejuni.showerhead.domain.SpreadEvent
import org.springframework.stereotype.Component
import java.util.*

const val TX_ID_LENGTH_CONSTRAINT = 3

@Component
class UUIDGenerator {

    fun generateTransactionId(): String {
        val idString = UUID.randomUUID().toString()
        return idString.substring(idString.length - TX_ID_LENGTH_CONSTRAINT, idString.length)
    }
}