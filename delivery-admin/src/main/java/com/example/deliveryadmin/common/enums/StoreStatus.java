package com.example.deliveryadmin.common.enums;

public enum StoreStatus {
    OPERATING("운영중"),
    ON_VACATION("휴업"),
    CLOSED("폐업"),
    UNDER_PREPARATION("준비중");

    private final String status;

    StoreStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
