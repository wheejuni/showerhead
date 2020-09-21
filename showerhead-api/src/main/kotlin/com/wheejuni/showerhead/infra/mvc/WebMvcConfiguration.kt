package com.wheejuni.showerhead.infra.mvc

import org.springframework.context.annotation.Configuration
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.servlet.config.annotation.EnableWebMvc
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport

@EnableWebMvc
@Configuration
class WebMvcConfiguration: WebMvcConfigurationSupport() {

    override fun addArgumentResolvers(
            argumentResolvers: MutableList<HandlerMethodArgumentResolver>) {
        argumentResolvers.add(UserIdentityResolver())
    }
}