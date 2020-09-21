package com.wheejuni.showerhead.application.scheduled

import org.slf4j.LoggerFactory
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

//language=SQL
const val UPDATE_QUERY = """
    UPDATE SPREAD_EVENT SET valid = FALSE WHERE (created_date_time < DATE_SUB(NOW(), INTERVAL 7 DAY)) 
"""

val logger = LoggerFactory.getLogger(SpreadEventInvalidateJob::class.java)

@Component
class SpreadEventInvalidateJob(private val jdbcTemplate: JdbcTemplate) {

    @Scheduled(cron ="0 0 0 * * *")
    fun invalidateExpiredEvents() {

        val updatedItems = jdbcTemplate.execute(UPDATE_QUERY)
        logger.info("{} items processed as invalid", updatedItems)
    }
}
