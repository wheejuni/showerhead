package com.wheejuni.showerhead.domain

import com.wheejuni.showerhead.application.RandomBasedSpreadAmountGenerator
import com.wheejuni.showerhead.application.TESTABLE_AMOUNT
import com.wheejuni.showerhead.domain.repositories.tid
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.util.*

internal class SpreadEventTest {

    @Test
    fun `객체 생성 테스트`() {

        //given
        var eventObject: SpreadEvent? = null

        //when
        val userId = "asdf"
        eventObject = SpreadEvent(tid, userId)

        //then
        assertTrue(eventObject.valid)
        assertEquals(tid, eventObject.transactionId)
    }

    @Test
    fun `분배된 금액 할당 테스트`() {

        //given
        val requestedReceiverId = "zella.ddo"
        val spreadStartId = "pony.tail"
        val generator = RandomBasedSpreadAmountGenerator(Random())

        //when
        val eventObject = SpreadEvent(tid, spreadStartId)
        eventObject.amount = TESTABLE_AMOUNT

        eventObject.setGeneratedSpreadAmount(generator.generateSpreadAmount(eventObject))

        val amount = eventObject.getAmountForReceiver(requestedReceiverId)

        //then
        assertTrue(eventObject.checkAlreadyProcessedReceiver(requestedReceiverId))
        assertTrue(amount.isMatchingReceiverId(requestedReceiverId))
    }
}
