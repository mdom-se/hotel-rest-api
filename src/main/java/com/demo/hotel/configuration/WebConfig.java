package com.demo.hotel.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

    private final WebConfigProperties webConfigProperties;

    public WebConfig(final WebConfigProperties webConfigProperties) {
        this.webConfigProperties = webConfigProperties;
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins(webConfigProperties.getAllowedOrigins())
                .allowedMethods(webConfigProperties.getAllowedMethods())
                .maxAge(webConfigProperties.getMaxAge())
                .allowedHeaders(webConfigProperties.getAllowedHeaders())
                .exposedHeaders(webConfigProperties.getExposedHeaders());
    }

}
