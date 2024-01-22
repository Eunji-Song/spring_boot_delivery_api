package com.example.deliveryadmin;

import org.mapstruct.Mapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RequestMapping;

@SpringBootApplication
@RequestMapping("/admin/")
public class DeliveryAdminApplication {
    public static void main(String[] args) {
        SpringApplication.run(DeliveryAdminApplication.class, args);
    }
}
