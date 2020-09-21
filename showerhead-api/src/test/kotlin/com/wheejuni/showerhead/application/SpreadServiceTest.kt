package com.wheejuni.showerhead.application

import com.wheejuni.showerhead.domain.TESTABLE_SPREAD_EVENT_REQUESTER_ID
import com.wheejuni.showerhead.domain.repositories.SpreadEventRepository
import com.wheejuni.showerhead.view.dto.SpreadRequestDto
import com.wheejuni.showerhead.view.handlerargument.RequesterIdentity
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

const val TEST_ROOM_ID = "A1234"

@SpringBootTest
internal class SpreadServiceTest {

    @Autowired
    private lateinit var service: SpreadService

    @Autowired
    private lateinit var repository: SpreadEventRepository

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

        //다른 방의 유저가 받을 수 없음
        assertThrows(IllegalArgumentException::class.java) {
            service.getAmountOnRequest(eventId, RequesterIdentity(receiverId, "other-room"))
        }

        //이미 받은 유저는 또 받을 수 없음
        assertThrows(IllegalArgumentException::class.java) {
            service.getAmountOnRequest(eventId, RequesterIdentity(receiverId, TEST_ROOM_ID))
        }

        //자신이 뿌린 건은 자신이 받을 수 없음
        assertThrows(java.lang.IllegalArgumentException::class.java) {
            service.getAmountOnRequest(eventId, testIdentity)
        }
    }

    @Test
    @Transactional
    fun `시간이 경과한 이벤트에 대한 받기 요청 예외처리`() {

        //given
        val eventId = service.newEvent(testRequest, testIdentity).generatedTransactionId

        //when
        val eventObject = repository.findByTransactionId(eventId)!!
        eventObject.createdDateTime = LocalDateTime.now().minusMinutes(11L)
        repository.save(eventObject)

        //then
        val expectedException = assertThrows<IllegalArgumentException> {
            service.getAmountOnRequest(eventId, RequesterIdentity("brian.king", TEST_ROOM_ID)) }

        assertEquals("받기 만료된 이벤트입니다.", expectedException.message)
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

        //다른 사람이 생성한 뿌리기 요청은 조회할 수 없음
        assertThrows<SecurityException> {
            service.getSpreadEventInfo(eventId, invalidIdentity)
        }
    }

}



