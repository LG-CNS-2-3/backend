package com.lgcns.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsUtils;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.List;

@Configuration
public class CorsConfig {
    private static final String ALLOWED_HEADERS = "authorization, Content-Type, Content-Length, Authorization, credential, X-XSRF-TOKEN, refreshtoken, RefreshToken";
    private static final String ALLOWED_METHODS = "GET, PUT, POST, DELETE, OPTIONS, PATCH";
    private static final String EXPOSE_HEADERS = "*, Authorization";
    private static final String MAX_AGE = "7200"; //2 hours (2 * 60 * 60)

    @Bean
    public WebFilter corsFilter(){
        return (ServerWebExchange ctx, WebFilterChain chain) -> {
            ServerHttpRequest request = ctx.getRequest();

            String origin = request.getHeaders().getOrigin();

            if(CorsUtils.isCorsRequest(request)){
                ServerHttpResponse response = ctx.getResponse();
                HttpHeaders headers = response.getHeaders();

                if(origin.startsWith("http://localhost:3000")){
                    headers.add("Access-Control-Allow-Origin", origin);
                }

                headers.add("Access-Control-Methods", ALLOWED_METHODS);
                headers.add("Access-Control-Max-Age", MAX_AGE);
                headers.add("Access-Control-Allow-Headers", ALLOWED_HEADERS);
                headers.add("Access-Control-Expose-Headers",  EXPOSE_HEADERS);
                headers.setAccessControlAllowCredentials(true);
                if (request.getMethod() == HttpMethod.OPTIONS) {
                    response.setStatusCode(HttpStatus.OK);
                    return Mono.empty();
                }
            }
            return chain.filter(ctx);
        };
    }
//    @Bean
    public CorsWebFilter corsWebFilter(){
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of("http://localhost:3000", "http://44.238.184.165:80"));
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
