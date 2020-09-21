package com.wheejuni.showerhead.application

import com.wheejuni.showerhead.domain.TESTABLE_SPREAD_EVENT_REQUESTER_ID
import com.wheejuni.showerhead.view.dto.SpreadRequestDto
import com.wheejuni.showerhead.view.handlerargument.RequesterIdentity
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import java.util.stream.Stream

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
        val idLength = generatedEventId.length

        //then
        assertTrue(idLength == 3)
    }

    @Test
    @Transactional
    fun `뿌리기 현황 조회 테스트`() {
        //given
        val receiverId = "zella.ddo"
        val eventId = service.newEvent(testRequest, testIdentity)

        //when
        val amount = service.getAmountOnRequest(eventId, RequesterIdentity(receiverId, TEST_ROOM_ID))

        //then
        assertNotNull(amount)
        assertThrows(IllegalArgumentException::class.java) {
            service.getAmountOnRequest(eventId, RequesterIdentity(receiverId, TEST_ROOM_ID))
        }
    }

}



