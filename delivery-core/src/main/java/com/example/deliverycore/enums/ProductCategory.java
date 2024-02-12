package com.example.deliverycore.enums;

public enum ProductCategory {
    CHINESE("중식", "CHINESE"),
    KOREAN("한식", "KOREAN"),
    WESTERN("양식","WESTERN"),
    SNACK("분식", "SNACK"),
    OTHER("기타", "OTHER");

    private final String name;
    private final String value;

    ProductCategory(String name, String value) {
        this.name = name;
        this.value = value;
    }
}
