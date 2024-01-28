package com.example.deliveryadmin.common.enums;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Arrays;

@Getter
public enum StoreStatus {
    OPERATING("운영중"),
    ON_VACATION("휴업"),
    CLOSED("폐업"),
    UNDER_PREPARATION("준비중");

    private final String value;

    StoreStatus(String value) {
        this.value = value;
    }

    public String getStatus() {
        return value;
    }

    public static StoreStatus of(String value) {
        if (value == null) {
            throw new IllegalArgumentException();
        }

        for (StoreStatus status : StoreStatus.values()) {
            if (status.getValue().equals(value)) {
                return status;
            }
        }

        throw new IllegalArgumentException("유효하지 않은 카테고리가 입력되었습니다.");
    }
}
