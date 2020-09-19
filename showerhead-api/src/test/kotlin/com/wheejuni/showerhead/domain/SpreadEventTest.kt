package com.wheejuni.showerhead.domain

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class SpreadEventTest {

    @Test
    fun `객체 생성 테스트`() {

        //given
        var eventObject: SpreadEvent? = null

        //when
        val transactionId = "xyz"
        eventObject = SpreadEvent(transactionId)

        //then
        assertTrue(eventObject.valid)
        assertEquals(transactionId, eventObject.transactionId)
    }

    @Test
    fun `입력값 테스트`() {

    }
}