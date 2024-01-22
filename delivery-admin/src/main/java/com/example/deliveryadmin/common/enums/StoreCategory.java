package com.example.deliveryadmin.common.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum StoreCategory {
    RESTAURANT("Restaurant"),
    CAFE("Cafe"),
    BAKERY("Bakery"),
    FAST_FOOD("Fast Food"),
    OTHER("Other");

    private final String displayName;
}
