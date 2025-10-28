package com.lgcns.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
public class CorsConfig {
    @Bean
    public CorsWebFilter corsWebFilter(){
        CorsConfiguration config = new CorsConfiguration();

//        config.setAllowedOriginPatterns(List.of(
//                "http://44.238.184.165:80",  // 운영 서버 (예시)
//                "http://localhost:80",       // 로컬 80
//                "http://localhost"
//        ));

        config.addAllowedOriginPattern("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

//        config.setAllowedHeaders(List.of("*"));
//        config.setAllowedMethods(List.of("*"));

//        config.setAllowedMethods(List.of(
//                "GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"
//        ));


        return new CorsWebFilter(source);
    }
}
