package com.example.deliveryadmin;

import org.mapstruct.Mapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.bind.annotation.RequestMapping;

@SpringBootApplication
@RequestMapping("/admin/")
@EnableJpaAuditing
@EntityScan(basePackages = "com.example")
@EnableJpaRepositories(basePackages = "com.example")
public class DeliveryAdminApplication {
    public static void main(String[] args) {
        SpringApplication.run(DeliveryAdminApplication.class, args);
    }
}
