package com.wheejuni.showerhead.application

import com.wheejuni.showerhead.domain.TESTABLE_SPREAD_EVENT_REQUESTER_ID
import com.wheejuni.showerhead.view.dto.SpreadRequestDto
import com.wheejuni.showerhead.view.handlerargument.RequesterIdentity
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional

const val TEST_ROOM_ID = "A1234"

@SpringBootTest
internal class SpreadServiceTest {

    @Autowired
    private lateinit var service: SpreadService

    val testIdentity = RequesterIdentity(TESTABLE_SPREAD_EVENT_REQUESTER_ID, TEST_ROOM_ID)
    val testRequest = SpreadRequestDto(TESTABLE_AMOUNT, TESTABLE_RECEIVER_COUNT)

    @Test
    fun `새로운 뿌리기 생성 테스트`() {
        //given
        val generatedEventId = service.newEvent(testRequest, testIdentity)

        //when
        val idLength = generatedEventId.generatedTransactionId.length

        //then
        assertTrue(idLength == 3)
    }

    @Test
    @Transactional
    fun `뿌리기 받기 테스트`() {
        //given
        val receiverId = "zella.ddo"
        val eventId = service.newEvent(testRequest, testIdentity).generatedTransactionId

        //when
        val amount = service.getAmountOnRequest(eventId, RequesterIdentity(receiverId, TEST_ROOM_ID))

        //then
        assertNotNull(amount)
        assertThrows(IllegalArgumentException::class.java) {
            service.getAmountOnRequest(eventId, RequesterIdentity(receiverId, TEST_ROOM_ID))
        }
    }

    @Test
    @Transactional
    fun `뿌리기 현황조회 기능 테스트`() {
        //given
        val requesterId = "zella.ddo"
        val testIdentity = RequesterIdentity(requesterId, TEST_ROOM_ID)

        val eventId = service.newEvent(testRequest, testIdentity).generatedTransactionId

        val invalidIdentity = RequesterIdentity(TESTABLE_SPREAD_EVENT_REQUESTER_ID, TEST_ROOM_ID)

        //when
        val fetchedEvent = service.getSpreadEventInfo(eventId, testIdentity)

        //then
        assertEquals(5, fetchedEvent.amounts.size)
        assertEquals(TESTABLE_AMOUNT, fetchedEvent.amount)
        assertTrue(fetchedEvent.valid)

        assertThrows<SecurityException> {
            service.getSpreadEventInfo(eventId, invalidIdentity)
        }
    }

}



