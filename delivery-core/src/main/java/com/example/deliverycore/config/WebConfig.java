package com.example.deliverycore.config;

import com.example.deliverycore.convert.enumConverters.StoreCategoryConverter;
import com.example.deliverycore.convert.enumConverters.StoreStatusConverter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Slf4j
@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new StoreCategoryConverter());
        registry.addConverter(new StoreStatusConverter());
    }
}
