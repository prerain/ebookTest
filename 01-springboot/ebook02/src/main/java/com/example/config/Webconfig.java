package com.example.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class Webconfig implements WebMvcConfigurer {
//    @Override
//    public void addFormatters(FormatterRegistry registry) {
//        registry.addConverter(new DateConvert());
//    }
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("/static/");
    }
    @Override
    public void addViewControllers(ViewControllerRegistry registry){

        registry.addViewController("welcome").setViewName("welcome");
        
    }
}
