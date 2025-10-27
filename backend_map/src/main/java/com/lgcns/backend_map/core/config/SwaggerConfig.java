package com.lgcns.backend_map.core.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {
    @Value("${gateway.url}")
    private String gatewayUrl;

    @Bean
    public OpenAPI openAPI(){
        return new OpenAPI()
                .info(new Info()
                        .title("Backend Map API")
                        .version("v1.0")
                        .description("Backend Map API"))
                .servers(List.of(
                        new Server()
                                .url(gatewayUrl)
                ));
    }
}
