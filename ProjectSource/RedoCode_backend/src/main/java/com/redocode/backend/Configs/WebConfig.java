package com.redocode.backend.Configs;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@ComponentScan
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry
        .addMapping("/**")
        .allowedMethods("PUT", "GET", "DELETE", "OPTIONS", "PATCH", "POST")
        .allowedOrigins("*")
        .allowedHeaders("*")
        .exposedHeaders("Authorization") // Expose certain headers, if needed
    //            .allowedHeaders("Authorization", "Content-Type")

    ;
  }
}
