package com.un.pingpong.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@EnableWebMvc
public class  WebCorsConfig  extends WebMvcConfigurerAdapter {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/**")
//                .allowedOrigins("https://angular-login-rest-api-ty5tr1.stackblitz.io")
//                .allowedMethods(HttpMethod.POST.name(), HttpMethod.GET.name(),HttpMethod.OPTIONS.name());
        registry.addMapping("/**")
                .allowedOrigins("https://angular-login-rest-api-ty5tr1.stackblitz.io")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "HEAD","OPTIONS")
                .allowCredentials(true);
    }
}