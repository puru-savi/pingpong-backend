package com.un.pingpong.config;

import com.un.pingpong.interceptor.RequestProcessingTimeInterceptor;
import com.un.pingpong.repository.UserSessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private UserSessionRepository userSessionRepository;
    @Override
    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(new RequestProcessingTimeInterceptor(userSessionRepository)).addPathPatterns("/**");
    }
}
