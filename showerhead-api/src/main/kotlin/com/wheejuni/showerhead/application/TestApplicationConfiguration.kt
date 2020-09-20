package com.wheejuni.showerhead.application

import org.springframework.boot.test.context.TestConfiguration
import java.util.*

const val TEST_RECEIVER_COUNT = 5

@TestConfiguration
class TestApplicationConfiguration: ApplicationConfiguration() {

    override fun randomAmountGenerator(): SpreadAmountGenerator {
        return RandomBasedSpreadAmountGenerator(TestableRandom())
    }
}

class TestableRandom: Random() {

    override fun nextInt(bound: Int): Int {
        return bound / TEST_RECEIVER_COUNT
    }
}