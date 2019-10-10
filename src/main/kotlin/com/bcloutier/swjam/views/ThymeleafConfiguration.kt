package com.bcloutier.swjam.views

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver
import org.thymeleaf.spring5.SpringTemplateEngine
import org.springframework.web.servlet.config.annotation.EnableWebMvc


@Configuration
class ThymeleafConfiguration {

//    @Bean
//    fun templateEngine(): SpringTemplateEngine {
//        val templateEngine = SpringTemplateEngine()
//        templateEngine.setTemplateResolver(thymeleafTemplateResolver())
//        return templateEngine
//    }

//    @Bean
//    fun thymeleafTemplateResolver(): SpringResourceTemplateResolver {
//        val templateResolver = SpringResourceTemplateResolver()
//        templateResolver.prefix = "/test/"
//        templateResolver.suffix = ".html"
//        templateResolver.setTemplateMode("HTML5")
//        return templateResolver
//    }
}
