package com.techlabs.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration configuration = new CorsConfiguration();
        
        configuration.setAllowCredentials(true);
        configuration.setAllowedOrigins(List.of("http://localhost:3000")); // Replace with your allowed origins
        configuration.setAllowedHeaders(Arrays.asList(
            "Origin", "Access-Control-Allow-Origin", "Content-Type",
            "Accept", "Jwt-Token", "Authorization", "Origin", "Accept", 
            "X-Requested-With", "Access-Control-Request-Method", 
            "Access-Control-Request-Headers"
        ));
        configuration.setExposedHeaders(Arrays.asList(
            "Origin", "Content-Type", "Accept", "Jwt-Token", "Authorization",
            "Access-Control-Allow-Origin", "Access-Control-Allow-Credentials", 
            "File-Name"
        ));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        
        source.registerCorsConfiguration("/**", configuration);
        
        return new CorsFilter(source);
    }
}
