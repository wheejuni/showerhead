package com.wheejuni.showerhead.domain

import org.springframework.util.StringUtils
import javax.persistence.*

@Entity
class SpreadAmount(amount: Int) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0

    @ManyToOne
    @JoinColumn(name = "TRANSACTION_ID")
    var transactionEvent: SpreadEvent? = null

    var amount: Int = amount
        private set

    var receiverId: String? = null
        private set

    fun allocateSpreadAmountToUser(userId: String) {
        this.receiverId = userId
    }

    fun isMatchingReceiverId(receiverId: String): Boolean {
        return this.receiverId.equals(receiverId, ignoreCase = false)
    }

    fun isValid(): Boolean = StringUtils.isEmpty(this.receiverId)
}
