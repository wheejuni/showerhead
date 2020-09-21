package com.wheejuni.showerhead.infra.mvc

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.servlet.config.annotation.EnableWebMvc
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport

@Configuration
class WebMvcConfiguration: WebMvcConfigurationSupport() {

    override fun addArgumentResolvers(argumentResolvers: MutableList<HandlerMethodArgumentResolver>) {
        super.addArgumentResolvers(argumentResolvers)
        argumentResolvers.add(UserIdentityResolver())
    }
}