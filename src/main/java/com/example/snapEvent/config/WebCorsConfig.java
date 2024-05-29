package com.example.snapEvent.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebCorsConfig implements WebMvcConfigurer {
    public static final String ALLOWED_METHOD_NAMES = "GET,HEAD,POST,PUT,DELETE,TRACE,OPTIONS,PATCH";

    @Override
    public void addCorsMappings(final CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedMethods(ALLOWED_METHOD_NAMES.split(","))
                .allowedOriginPatterns("http://localhost:3000", "http://localhost:5173", "https://snapevent.site", "http://localhost:8080")
                .exposedHeaders("Authorization")
                .exposedHeaders("Set-Cookie")
                .allowedHeaders("Cookie")
                .allowCredentials(true); // 자바스크립트 코드가 응답 자체에 접근할 수 있게 허용
    }
}
