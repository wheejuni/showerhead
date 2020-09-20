package com.wheejuni.showerhead.application

import com.wheejuni.showerhead.domain.TESTABLE_SPREAD_EVENT
import com.wheejuni.showerhead.domain.repositories.tid
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
internal class SpreadEventValidatorTest {

    @Autowired
    private lateinit var validator: SpreadEventValidator

    @Test
    fun `신규 이벤트 생성 테스트`() {
        //given
        val eventObject = TESTABLE_SPREAD_EVENT

        //when
        validator.cacheNewEvent(eventObject)

        //then
        validator.isValidRequest(tid)
    }
}