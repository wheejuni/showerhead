package com.wheejuni.showerhead.infra.mvc

import com.wheejuni.showerhead.view.dto.ErrorResponse
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ExceptionControllerAdvice {

    @ExceptionHandler(IllegalArgumentException::class)
    fun handleIllegalArgumentException(exception: IllegalArgumentException): ErrorResponse {
        return ErrorResponse(400, exception.message ?: "잘못된 요청입니다.")
    }

    @ExceptionHandler(SecurityException::class)
    fun handleSecurityRelatedErrors(exception: SecurityException): ErrorResponse {
        return ErrorResponse(403, exception.message ?: "잘못된 접근입니다.")
    }
}
