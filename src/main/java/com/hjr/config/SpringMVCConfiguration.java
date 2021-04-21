package com.hjr.config;

import com.hjr.converter.StringToLocalDateConverter;
import com.hjr.interceptor.HasAdminSessionInterceptor;
import com.hjr.interceptor.HasStudentSessionInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration(proxyBeanMethods = false)
@Slf4j
public class SpringMVCConfiguration implements WebMvcConfigurer {

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new StringToLocalDateConverter());
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new HasStudentSessionInterceptor())
                .addPathPatterns("/student", "/checkpage", "/check", "/studentinfo", "/checkhistory", "/updatestudentinfo")
                .order(0);

        registry.addInterceptor(new HasAdminSessionInterceptor())
                .addPathPatterns("/admin")
                .order(1);
    }
}
