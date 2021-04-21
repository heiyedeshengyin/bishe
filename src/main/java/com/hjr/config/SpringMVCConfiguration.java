package com.hjr.config;

import com.hjr.converter.StringToLocalDateConverter;
import com.hjr.interceptor.HasAdminSessionInterceptor;
import com.hjr.interceptor.HasStudentSessionInterceptor;
import com.hjr.interceptor.IndexInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration(proxyBeanMethods = false)
public class SpringMVCConfiguration implements WebMvcConfigurer {

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new StringToLocalDateConverter());
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new IndexInterceptor())
                .addPathPatterns("/");

        registry.addInterceptor(new HasStudentSessionInterceptor())
                .addPathPatterns("/student/**");

        registry.addInterceptor(new HasAdminSessionInterceptor())
                .addPathPatterns("/admin/**");
    }
}
