package com.example.deliveryadmin.common.config;

import com.example.deliveryadmin.common.convert.enumConverters.StoreCategoryConverter;
import com.example.deliveryadmin.common.convert.enumConverters.StoreStatusConverter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Slf4j
@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addFormatters(FormatterRegistry registry) {
        log.info("WEBCONFIG");
        registry.addConverter(new StoreCategoryConverter());
        registry.addConverter(new StoreStatusConverter());
    }
}
