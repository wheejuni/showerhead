package com.wheejuni.showerhead.application

import com.wheejuni.showerhead.domain.CacheableSpreadEvent
import com.wheejuni.showerhead.domain.SpreadAmount
import com.wheejuni.showerhead.domain.SpreadEvent
import com.wheejuni.showerhead.domain.repositories.SpreadEventRepository
import com.wheejuni.showerhead.view.dto.SpreadRequestDto
import com.wheejuni.showerhead.view.handlerargument.RequesterIdentity
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
class SpreadService(
        private val amountGenerator: SpreadAmountGenerator,
        private val uuidGenerator: UUIDGenerator,
        private val validator: SpreadEventValidator,
        private val repository: SpreadEventRepository
) {

    @Transactional
    fun newEvent(request: SpreadRequestDto, identity: RequesterIdentity): String {
        val generatedEvent = SpreadEvent.fromGenerationRequest(request, identity, uuidGenerator.generateTransactionId())
        generatedEvent.setGeneratedSpreadAmount(amountGenerator.generateSpreadAmount(generatedEvent))
        generatedEvent.roomId = identity.roomId

        validator.cacheNewEvent(CacheableSpreadEvent(generatedEvent.transactionId, identity.userId))

        return repository.save(generatedEvent).transactionId
    }

    @Transactional
    fun getAmountOnRequest(transactionId: String, identity: RequesterIdentity): SpreadAmount {
        if(!validator.isValidRequest(transactionId, identity.userId)) {
            throw IllegalArgumentException("존재하지 않거나, 유효하지 않은 요청입니다.")
        }

        val event = repository.findByTransactionIdAndRoomId(transactionId, identity.roomId)
                ?: throw IllegalArgumentException("존재하지 않는 요청입니다.")

        return event.getAmountForReceiver(identity.userId)
    }

    @Transactional
    fun getSpreadEventInfo(transactionId: String, identity: RequesterIdentity): SpreadEvent {
        val eventObject = repository.findByTransactionIdAndValid(transactionId, true)
                ?: throw IllegalArgumentException("존재하지 않는 요청이거나 만료된 요청입니다.")

        if(eventObject.generatorId != identity.userId) {
            throw SecurityException("조회할 권한이 없습니다.")
        }

        return eventObject
    }
}
