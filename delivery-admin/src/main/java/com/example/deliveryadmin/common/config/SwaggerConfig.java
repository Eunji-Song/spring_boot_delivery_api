package com.example.deliveryadmin.common.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI api() {
        return new OpenAPI().info(apiInfo());
    }

    private Info apiInfo() {
        return new Info().title("배달 서비스 Admin API").description("Admin 전용 API").version("0.0.1");
    }

}


