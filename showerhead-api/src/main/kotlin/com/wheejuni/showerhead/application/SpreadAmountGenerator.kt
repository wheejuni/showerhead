package com.wheejuni.showerhead.application

import com.wheejuni.showerhead.domain.SpreadAmount
import com.wheejuni.showerhead.domain.SpreadEvent

interface SpreadAmountGenerator {
    fun generateSpreadAmount(spreadEvent: SpreadEvent): List<SpreadAmount>
}