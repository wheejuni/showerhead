package com.wheejuni.showerhead.application

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import org.springframework.scheduling.annotation.EnableScheduling
import java.util.*

@Configuration
@EnableJpaAuditing
@EnableScheduling
class ApplicationConfiguration {

    @Bean
    fun randomAmountGenerator(): SpreadAmountGenerator {
        return RandomBasedSpreadAmountGenerator(Random())
    }
}
