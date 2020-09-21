package com.wheejuni.showerhead.view

import com.wheejuni.showerhead.application.SpreadService
import com.wheejuni.showerhead.domain.NewSpreadEventResponse
import com.wheejuni.showerhead.domain.SpreadAmountResponse
import com.wheejuni.showerhead.domain.SpreadEventDetails
import com.wheejuni.showerhead.view.dto.SpreadRequestDto
import com.wheejuni.showerhead.view.handlerargument.RequesterIdentity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1")
class ApiV1Controller(
        private val spreadService: SpreadService) {

    @PostMapping("/spread")
    fun generateNewEventApi(identity: RequesterIdentity, @RequestBody request: SpreadRequestDto): NewSpreadEventResponse {
        return spreadService.newEvent(request, identity)
    }

    @GetMapping("/getmoney/{transactionId}")
    fun getMoneyFromSpreadEventApi(identity: RequesterIdentity, @PathVariable("transactionId") transactionId: String): SpreadAmountResponse {
        return spreadService.getAmountOnRequest(transactionId, identity).toResponse()
    }

    @GetMapping("/details/{transactionId}")
    fun getEventDetailsApi(identity: RequesterIdentity, @PathVariable("transactionId")transactionId: String): SpreadEventDetails {
        return spreadService.getSpreadEventDetails(transactionId, identity)
    }
}
