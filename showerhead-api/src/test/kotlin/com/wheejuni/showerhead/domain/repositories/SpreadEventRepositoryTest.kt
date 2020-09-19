package com.wheejuni.showerhead.domain.repositories

import com.wheejuni.showerhead.domain.SpreadEvent
import org.junit.jupiter.api.Assertions.assertDoesNotThrow
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.junit4.SpringRunner
import javax.transaction.Transactional

@RunWith(SpringRunner::class)
@DataJpaTest
internal class SpreadEventRepositoryTest {

    @Autowired
    private lateinit var repository: SpreadEventRepository

    @Test
    @Transactional
    fun `find-by-transactionid 테스트`() {

        //given
        val tid = "xyz"
        val event = SpreadEvent(tid)

        //when
        repository.save(event)

        //then
        assertDoesNotThrow { repository.findByTransactionId(tid)!!.transactionId }
        assertThrows(NullPointerException::class.java) {
            repository.findByTransactionId("abc")!!.transactionId
        }
    }
}