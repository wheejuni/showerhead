package com.wheejuni.showerhead.view.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class SpreadRequestDto (
        @field:JsonProperty("request_amount")
        val requestAmount: Int = 0,

        @field:JsonProperty("request_receiver_count")
        val requestReceiverCount: Int = 0)