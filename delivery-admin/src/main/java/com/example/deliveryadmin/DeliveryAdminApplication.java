package com.example.deliveryadmin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.web.bind.annotation.RequestMapping;

@SpringBootApplication(scanBasePackages = "com.example")
@RequestMapping("/admin/")
@EntityScan(basePackages = "com.example.deliverycore")
public class DeliveryAdminApplication {
    public static void main(String[] args) {
        SpringApplication.run(DeliveryAdminApplication.class, args);
    }
}
