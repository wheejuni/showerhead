package com.wheejuni.showerhead.domain.repositories

import com.wheejuni.showerhead.application.RandomBasedSpreadAmountGenerator
import com.wheejuni.showerhead.application.TESTABLE_AMOUNT
import com.wheejuni.showerhead.domain.SpreadEvent
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertDoesNotThrow
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.junit4.SpringRunner
import java.util.*
import javax.transaction.Transactional

const val tid = "xyz"
const val uid = "asb"

@RunWith(SpringRunner::class)
@DataJpaTest
internal class SpreadEventRepositoryTest {


    @Autowired
    private lateinit var repository: SpreadEventRepository

    private lateinit var testObject: SpreadEvent

    @BeforeEach
    fun `객체 저장`() {

        val event = SpreadEvent(tid, uid)
        repository.save(event)
    }

    @Test
    @Transactional
    fun `find-by-transactionid 테스트`() {

        //given
        val event = repository.findByTransactionId(tid)

        //then
        assertDoesNotThrow { event!!.transactionId }

        assertThrows(NullPointerException::class.java) {
            repository.findByTransactionId("abc")!!.transactionId
        }
    }

    @Test
    @Transactional
    fun `findby-tid-and-uid 테스트`() {

        //given
        val event = repository.findByTransactionIdAndGeneratorId(tid, uid)

        //then
        assertDoesNotThrow { event!!.transactionId }
    }

    @Test
    @Transactional
    fun `spreadevent-spreadamount 매핑 관계 테스트`() {
        //given
        val requestedReceiverId = "zella.ddo"
        val spreadStartId = "pony.tail"
        val generator = RandomBasedSpreadAmountGenerator(Random())

        //when
        val eventObject = SpreadEvent(tid, spreadStartId)
        eventObject.amount = TESTABLE_AMOUNT

        eventObject.setGeneratedSpreadAmount(generator.generateSpreadAmount(eventObject))

        repository.save(eventObject)
        val persistedEvent = repository.findByTransactionIdAndGeneratorId(tid, spreadStartId)

        val existingEvent = persistedEvent ?: SpreadEvent(tid, spreadStartId)
        val amount = existingEvent.getAmountForReceiver(requestedReceiverId)

        repository.save(existingEvent)

        //then
        val persistedAllocatedEvent = repository.findByTransactionIdAndGeneratorId(tid, spreadStartId)
        Assertions.assertTrue(persistedAllocatedEvent!!.checkAlreadyProcessedReceiver(requestedReceiverId))
    }
}