package com.example.deliveryadmin.common.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

public enum StoreCategory {
    RESTAURANT("Restaurant"),
    CAFE("Cafe"),
    BAKERY("Bakery"),
    FAST_FOOD("Fast Food"),
    OTHER("Other");

    private final String storeCategory;

    StoreCategory(String storeCategory) {
        this.storeCategory = storeCategory;
    }
}
