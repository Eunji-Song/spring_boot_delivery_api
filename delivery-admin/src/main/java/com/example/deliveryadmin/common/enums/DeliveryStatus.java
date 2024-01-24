package com.example.deliveryadmin.common.enums;

public enum DeliveryStatus {
    AWAITING_PICKUP("Awaiting Pickup"),    // 픽업 대기 중
    OUT_FOR_DELIVERY("Out for Delivery"),  // 배송 중
    DELIVERED("Delivered"),                // 배송 완료
    DELIVERY_FAILED("Delivery Failed");    // 배송 실패

    private final String deliveryStatus;

    DeliveryStatus(String deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }
}
