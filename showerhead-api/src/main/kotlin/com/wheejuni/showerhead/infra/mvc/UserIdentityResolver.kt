package com.wheejuni.showerhead.infra.mvc

import com.wheejuni.showerhead.view.handlerargument.RequesterIdentity
import org.springframework.core.MethodParameter
import org.springframework.stereotype.Component
import org.springframework.util.StringUtils
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.method.support.ModelAndViewContainer

const val USER_ID_HEADER = "X-USER-ID"
const val ROOM_ID_HEADER = "X-ROOM-ID"

class UserIdentityResolver: HandlerMethodArgumentResolver {
    override fun supportsParameter(parameter: MethodParameter): Boolean = parameter.parameterType == RequesterIdentity::class.java

    override fun resolveArgument(parameter: MethodParameter, mavContainer: ModelAndViewContainer?, webRequest: NativeWebRequest, binderFactory: WebDataBinderFactory?): Any? {
        val userId = webRequest.getHeader(USER_ID_HEADER) ?: ""
        val roomId = webRequest.getHeader(ROOM_ID_HEADER) ?: ""

        if(StringUtils.isEmpty(userId) || StringUtils.isEmpty(roomId)) {
            throw UnsupportedOperationException("필수 헤더가 빠져 있습니다.")
        }

        return RequesterIdentity(userId, roomId)
    }
}