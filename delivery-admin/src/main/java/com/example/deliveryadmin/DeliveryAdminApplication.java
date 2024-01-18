package com.example.deliveryadmin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class DeliveryAdminApplication {
    public static void main(String[] args) {
        SpringApplication.run(DeliveryAdminApplication.class, args);
    }
}
