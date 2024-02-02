package com.example.deliveryadmin.common.convert.enumConverters;

import com.example.deliverycore.enums.StoreCategory;
import com.example.deliverycore.enums.StoreStatus;
import org.springframework.core.convert.converter.Converter;

public class StoreStatusConverter implements Converter<String, StoreStatus> {
    @Override
    public StoreStatus convert(String storeStatus) {
        return StoreStatus.of(storeStatus);
    }
}