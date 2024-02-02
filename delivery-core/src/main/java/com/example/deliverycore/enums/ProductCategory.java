package com.example.deliverycore.enums;

public enum ProductCategory {
    CHINESE("중식"),
    KOREAN("한식"),
    WESTERN("양식"),
    SNACK("분식"),
    OTHER("기타");

    private final String productCategory;

    ProductCategory(String productCategory) {
        this.productCategory = productCategory;
    }
}
