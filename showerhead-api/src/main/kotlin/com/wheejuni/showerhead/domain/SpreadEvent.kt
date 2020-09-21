package com.wheejuni.showerhead.domain

import com.wheejuni.showerhead.view.dto.SpreadRequestDto
import com.wheejuni.showerhead.view.handlerargument.RequesterIdentity
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@EntityListeners(AuditingEntityListener::class)
class SpreadEvent(val transactionId: String, val generatorId: String) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0

    @OneToMany(mappedBy = "transactionEvent", cascade = [CascadeType.ALL])
    var amounts: MutableList<SpreadAmount> = mutableListOf()

    var roomId: String? = null

    var amount: Int = 0

    var receiverCount: Int = 0

    @CreatedDate
    lateinit var createdDateTime: LocalDateTime

    var valid: Boolean = true
        private set

    companion object {

        @JvmStatic
        fun fromGenerationRequest(dto: SpreadRequestDto, identity: RequesterIdentity, tid: String): SpreadEvent {
            val generatedObject = SpreadEvent(tid, identity.userId)

            generatedObject.amount = dto.requestAmount
            generatedObject.receiverCount = dto.requestReceiverCount
            return generatedObject
        }
    }

    fun invalidate() {
        this.valid = false
    }

    fun setGeneratedSpreadAmount(amounts: List<SpreadAmount>) {
        amounts.forEach {
            it.transactionEvent = this
            this.amounts.add(it) }
    }

    fun checkAlreadyProcessedReceiver(receiverId: String): Boolean {
        return amounts
                .map { it.isMatchingReceiverId(receiverId) }
                .filter { it }
                .any()
    }

    fun getTakenAmount(): Int {
        return this.amounts.filter {
            it.isAvailable().not()
        }.sumBy {
            it.amount
        }
    }

    fun getTakenDetails(): List<SpreadAmountResponse> {
        return this.amounts.filter {
            it.isAvailable().not()
        }.map { it.toResponse() }.toCollection(mutableListOf())
    }

    fun getAmountForReceiver(receiverId: String): SpreadAmount {

        if(this.amounts.any { it.isMatchingReceiverId(receiverId) }) {
            throw IllegalArgumentException("이미 뿌리기를 받은 사용자입니다.")
        }

        val designatedAmount = this.amounts.first { it.isAvailable() }
        this.amounts.remove(designatedAmount)

        designatedAmount.allocateSpreadAmountToUser(receiverId)
        this.amounts.add(designatedAmount)

        return designatedAmount
    }

    fun toEventResponse(): NewSpreadEventResponse {
        return NewSpreadEventResponse(this.transactionId)
    }

    fun toEventDetails(): SpreadEventDetails {
        return SpreadEventDetails(
                createdDateTime = this.createdDateTime,
                amount = this.amount,
                takenAmount = this.getTakenAmount(),
                takenDetails = this.getTakenDetails())
    }
}

data class NewSpreadEventResponse(
        val generatedTransactionId: String)

data class SpreadEventDetails(
        val createdDateTime: LocalDateTime,
        val amount: Int,
        val takenAmount: Int,
        val takenDetails: List<SpreadAmountResponse> = emptyList())
