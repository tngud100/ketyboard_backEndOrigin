package com.example.keyboard.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsMvcConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry corsRegistry) {

        corsRegistry.addMapping("/**")
//                .allowedOrigins("http://localhost:3000");
                .allowedOrigins("https://d585ihjutu4dg.cloudfront.net");
//                .allowedOrigins("*");
    }
}