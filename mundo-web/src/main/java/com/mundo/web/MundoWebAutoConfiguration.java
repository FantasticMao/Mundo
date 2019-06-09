package com.mundo.web;

import com.mundo.web.interceptor.CheckCsrfInterceptor;
import com.mundo.web.interceptor.CheckLoginInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * MundoWebAutoConfiguration
 *
 * @author maodh
 * @since 2017/8/2
 */
@Configuration
@ComponentScan(value = "com.mundo.web.mvc")
public class MundoWebAutoConfiguration implements WebMvcConfigurer {
    private static final Logger LOGGER = LoggerFactory.getLogger(MundoWebAutoConfiguration.class);

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        LOGGER.info("Registering [CheckCsrfInterceptor] for Spring MVC!");
        registry.addInterceptor(checkCsrfInterceptor());
        LOGGER.info("Registering [CheckLoginInterceptor] for Spring MVC!");
        registry.addInterceptor(checkLoginInterceptor());
    }

    @Bean
    @ConditionalOnMissingBean
    CheckCsrfInterceptor checkCsrfInterceptor() {
        return new CheckCsrfInterceptor();
    }

    @Bean
    @ConditionalOnMissingBean
    CheckLoginInterceptor checkLoginInterceptor() {
        return new CheckLoginInterceptor();
    }
}