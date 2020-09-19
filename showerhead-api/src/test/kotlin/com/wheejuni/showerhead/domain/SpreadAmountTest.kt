package com.wheejuni.showerhead.domain

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class SpreadAmountTest {

    @Test
    fun `객체 생성 테스트`() {

        //given
        val amount = 10000

        //when
        val amountObject = SpreadAmount(amount)

        //then
        assertEquals(amount, amountObject.amount)
    }
}