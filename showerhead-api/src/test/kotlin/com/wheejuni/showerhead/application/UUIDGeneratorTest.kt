package com.wheejuni.showerhead.application

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class UUIDGeneratorTest {

    private lateinit var generator: UUIDGenerator

    @BeforeEach
    fun `generator 객체 생성`() {
        generator = UUIDGenerator()
    }

    @Test
    fun `uuid 생성 테스트`() {

        //given
        var idString = ""

        //when
        idString = generator.generateTransactionId()

        //then
        print(idString)
        assertEquals(3, idString.length)
    }
}