package com.wheejuni.showerhead.application

import com.wheejuni.showerhead.domain.SpreadEvent

class SpreadService(
        private val amountGenerator: SpreadAmountGenerator,
        private val uuidGenerator: UUIDGenerator
) {

    fun newEvent(): SpreadEvent {
        return SpreadEvent("", "")
    }
}