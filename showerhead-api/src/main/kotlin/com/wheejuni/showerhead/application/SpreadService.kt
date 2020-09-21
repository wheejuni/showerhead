package com.wheejuni.showerhead.application

import com.wheejuni.showerhead.domain.*
import com.wheejuni.showerhead.domain.repositories.SpreadEventRepository
import com.wheejuni.showerhead.view.dto.SpreadRequestDto
import com.wheejuni.showerhead.view.handlerargument.RequesterIdentity
import org.redisson.api.RedissonClient
import org.springframework.stereotype.Service
import java.util.concurrent.TimeUnit
import javax.transaction.Transactional

const val REDISSON_LOCK_ID = "spread-get-lock"
const val LOCK_WAIT_DURATION_IN_MS = 100L
const val LOCK_PERSIST_DURATION_IN_MS = 50L

@Service
class SpreadService(
        private val amountGenerator: SpreadAmountGenerator,
        private val uuidGenerator: UUIDGenerator,
        private val validator: SpreadEventValidator,
        private val repository: SpreadEventRepository,
        private val redissonClient: RedissonClient
) {

    @Transactional
    fun newEvent(request: SpreadRequestDto, identity: RequesterIdentity): NewSpreadEventResponse {
        val generatedEvent = SpreadEvent.fromGenerationRequest(request, identity, uuidGenerator.generateTransactionId())
        generatedEvent.setGeneratedSpreadAmount(amountGenerator.generateSpreadAmount(generatedEvent))
        generatedEvent.roomId = identity.roomId

        validator.cacheNewEvent(CacheableSpreadEvent(generatedEvent.transactionId, identity.userId))

        return repository.save(generatedEvent).toEventResponse()
    }

    @Transactional
    fun getAmountOnRequest(transactionId: String, identity: RequesterIdentity): SpreadAmount {
        val lock = redissonClient.getLock("$transactionId:${identity.userId}")

        val successfulLock = lock.tryLock(LOCK_WAIT_DURATION_IN_MS, LOCK_PERSIST_DURATION_IN_MS, TimeUnit.MILLISECONDS)

        if(successfulLock){
            if(!validator.isValidRequest(transactionId, identity.userId)) {
                throw IllegalArgumentException("유효하지 않은 요청입니다.")
            }

            try {
                val event = repository.findByTransactionIdAndRoomId(transactionId, identity.roomId)
                        ?: throw IllegalArgumentException("유효하지 않은 요청입니다.")

                return event.getAmountForReceiver(identity.userId)

            } catch (exception: IllegalArgumentException) {
                throw exception
            }
        }

        throw ConcurrentModificationException("다른 서버에서 요청을 처리중입니다.")
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

    @Transactional
    fun getSpreadEventDetails(transactionId: String, identity: RequesterIdentity): SpreadEventDetails =
            getSpreadEventInfo(transactionId, identity).toEventDetails()
}
