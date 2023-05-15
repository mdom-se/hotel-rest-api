package com.demo.hotel.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "web.cors")
public class WebConfigProperties {

    private String[] allowedOrigins;
    private String[] allowedMethods;
    private int maxAge;
    private String[] allowedHeaders;
    private String[] exposedHeaders;

    public String[] getAllowedOrigins() {
        return allowedOrigins;
    }

    public void setAllowedOrigins(String[] allowedOrigins) {
        this.allowedOrigins = allowedOrigins;
    }

    public String[] getAllowedMethods() {
        return allowedMethods;
    }

    public void setAllowedMethods(String[] allowedMethods) {
        this.allowedMethods = allowedMethods;
    }

    public int getMaxAge() {
        return maxAge;
    }

    public void setMaxAge(int maxAge) {
        this.maxAge = maxAge;
    }

    public String[] getAllowedHeaders() {
        return allowedHeaders;
    }

    public void setAllowedHeaders(String[] allowedHeaders) {
        this.allowedHeaders = allowedHeaders;
    }

    public String[] getExposedHeaders() {
        return exposedHeaders;
    }

    public void setExposedHeaders(String[] exposedHeaders) {
        this.exposedHeaders = exposedHeaders;
    }
}
