package com.example.deliverycore.convert.enumConverters;

import com.example.deliverycore.enums.StoreCategory;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StoreCategoryConverter implements Converter<String, StoreCategory> {
    @Override
    public StoreCategory convert(String storeCategory) {
        return StoreCategory.of(storeCategory);
    }
}