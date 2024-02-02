package com.example.deliverycore.enums;

public enum OrderStatus {
    PENDING("대기중"),

    COMPLETED("주문 처리 완료"),

    CANCELED("주문 취소 처리");

    private final String orderStatus;

    OrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }
}
