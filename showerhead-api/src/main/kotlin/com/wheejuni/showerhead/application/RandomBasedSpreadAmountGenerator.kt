package com.wheejuni.showerhead.application

import com.wheejuni.showerhead.domain.SpreadAmount
import com.wheejuni.showerhead.domain.SpreadEvent
import java.util.*

class RandomBasedSpreadAmountGenerator(val random: Random): SpreadAmountGenerator {

    override fun generateSpreadAmount(spreadEvent: SpreadEvent): List<SpreadAmount> {
        return (0..spreadEvent.receiverCount)
                .map mapper@{
                    val remainingAmount = spreadEvent.amount

                    if (it == spreadEvent.receiverCount - 1) {
                        return@mapper SpreadAmount(remainingAmount)
                    }

                    val randomAmount = random.nextInt(remainingAmount)
                    spreadEvent.amount -= randomAmount

                    return@mapper SpreadAmount(randomAmount)
                }
                .toCollection(mutableListOf())
    }
}