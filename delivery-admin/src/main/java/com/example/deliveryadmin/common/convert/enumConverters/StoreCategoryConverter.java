package com.example.deliveryadmin.common.convert.enumConverters;

import com.example.deliverycore.enums.StoreCategory;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Convert;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StoreCategoryConverter implements Converter<String, StoreCategory> {
    @Override
    public StoreCategory convert(String storeCategory) {
        return StoreCategory.of(storeCategory);
    }
}