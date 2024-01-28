package com.example.deliveryadmin.common.enums;

import lombok.Getter;

@Getter
public enum StoreCategory {
    RESTAURANT("Restaurant"),
    CAFE("Cafe"),
    BAKERY("Bakery"),
    FAST_FOOD("Fast Food"),
    OTHER("Other");

    private final String value;

    StoreCategory(String value) {
        this.value = value;
    }


    public static StoreCategory of(String value) {
        if (value == null) {
            throw new IllegalArgumentException();
        }

        for (StoreCategory category : StoreCategory.values()) {
            if (category.getValue().equals(value)) {
                return category;
            }
        }

        throw new IllegalArgumentException();
    }


}
