package com.wheejuni.showerhead.domain

import org.springframework.data.annotation.CreatedDate
import java.time.LocalDateTime
import javax.persistence.*

@Entity
class SpreadEvent(val transactionId: String, val generatorId: String) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0

    @OneToMany(mappedBy = "transactionEvent")
    var amounts: List<SpreadAmount> = emptyList()

    var roomId: String? = null

    var amount: Int = 0

    var receiverCount: Int = 0

    @CreatedDate
    lateinit var createdDateTime: LocalDateTime

    var valid: Boolean = true
        private set
}