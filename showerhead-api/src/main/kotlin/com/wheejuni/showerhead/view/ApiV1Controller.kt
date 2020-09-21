package com.wheejuni.showerhead.view

import com.wheejuni.showerhead.application.SpreadService
import com.wheejuni.showerhead.domain.NewSpreadEventResponse
import com.wheejuni.showerhead.view.dto.SpreadRequestDto
import com.wheejuni.showerhead.view.handlerargument.RequesterIdentity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1")
class ApiV1Controller(
        private val spreadService: SpreadService) {

    @PostMapping("/new")
    fun generateNewEventApi(identity: RequesterIdentity, @RequestBody request: SpreadRequestDto): NewSpreadEventResponse {
        return spreadService.newEvent(request, identity)
    }

    @GetMapping("/details/{transactionId}")
    fun getEventDetailsApi(identity: RequesterIdentity, @RequestParam("transactionId")transactionId: String) {

    }
}