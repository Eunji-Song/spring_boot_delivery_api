package com.example.deliverycore.enums;

import lombok.Getter;

@Getter
public enum StoreCategory {
    CHINESE("중식", "CHINESE"),
    KOREAN("한식", "KOREAN"),
    WESTERN("양식","WESTERN"),
    SNACK("분식", "SNACK"),
    CAFE("카페/디저트", "CAFE"),
    OTHER("기타", "OTHER"),
    FAST_FOOD("패스트푸드", "FAST_FOOD");

    private final String name;
    private final String value;

    StoreCategory(String name, String value) {
        this.name = name;
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
