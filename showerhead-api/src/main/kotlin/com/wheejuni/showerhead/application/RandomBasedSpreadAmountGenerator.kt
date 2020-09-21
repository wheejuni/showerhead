package com.wheejuni.showerhead.application

import com.wheejuni.showerhead.domain.SpreadAmount
import com.wheejuni.showerhead.domain.SpreadEvent
import java.util.*

class RandomBasedSpreadAmountGenerator(val random: Random): SpreadAmountGenerator {

    override fun generateSpreadAmount(spreadEvent: SpreadEvent): List<SpreadAmount> {
        var remainingAmount = spreadEvent.amount

        return (0 until spreadEvent.receiverCount)
                .map mapper@{

                    if (it == spreadEvent.receiverCount - 1) {
                        return@mapper SpreadAmount(remainingAmount)
                    }

                    val randomAmount = random.nextInt(remainingAmount)
                    remainingAmount -= randomAmount

                    return@mapper SpreadAmount(randomAmount)
                }
                .toCollection(mutableListOf())
    }
}