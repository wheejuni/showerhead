package com.wheejuni.showerhead.application

import com.wheejuni.showerhead.domain.SpreadEvent
import com.wheejuni.showerhead.domain.repositories.tid
import com.wheejuni.showerhead.domain.repositories.uid
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.*

const val TESTABLE_RECEIVER_COUNT = 5
const val TESTABLE_AMOUNT = 60000

internal class RandomBasedSpreadAmountGeneratorTest {

    private lateinit var generator: SpreadAmountGenerator
    private lateinit var event: SpreadEvent

    @BeforeEach
    fun `객체 생성`() {
        generator = RandomBasedSpreadAmountGenerator(EvenRandom())

        event = SpreadEvent(tid, uid)
        event.amount = TESTABLE_AMOUNT
        event.receiverCount = TESTABLE_RECEIVER_COUNT
    }

    @Test
    fun `배분 로직 테스트`() {
        //given
        val generatedAmounts = generator.generateSpreadAmount(event)

        //when
        val evenAmount = generatedAmounts[0].amount

        //then
        assertEquals(12000, evenAmount)
    }
}

class EvenRandom: Random() {
    override fun nextInt(bound: Int): Int {
        return bound / TESTABLE_RECEIVER_COUNT
    }
}