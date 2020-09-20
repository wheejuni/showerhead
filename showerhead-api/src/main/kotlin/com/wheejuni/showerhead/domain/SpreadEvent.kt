package com.wheejuni.showerhead.domain

import org.springframework.data.annotation.CreatedDate
import java.time.LocalDateTime
import javax.persistence.*

@Entity
class SpreadEvent(val transactionId: String, val generatorId: String) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0

    @OneToMany(mappedBy = "transactionEvent", cascade = [CascadeType.MERGE])
    var amounts: MutableList<SpreadAmount> = mutableListOf()

    var roomId: String? = null

    var amount: Int = 0

    var receiverCount: Int = 0

    @CreatedDate
    lateinit var createdDateTime: LocalDateTime

    var valid: Boolean = true
        private set

    fun invalidate() {
        this.valid = false
    }

    fun setGeneratedSpreadAmount(amounts: List<SpreadAmount>) {
        this.amounts = amounts.toCollection(mutableListOf())
    }

    fun checkAlreadyProcessedReceiver(receiverId: String): Boolean {
        return amounts
                .map { it.isMatchingReceiverId(receiverId) }
                .any { it }
                .not()
    }

    fun getAmountForReceiver(receiverId: String): SpreadAmount {
        val designatedAmount = this.amounts.first { it.isValid() }
        this.amounts.remove(designatedAmount)

        designatedAmount.allocateSpreadAmountToUser(receiverId)
        this.amounts.add(designatedAmount)

        return designatedAmount
    }
}
