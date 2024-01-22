package com.example.deliveryadmin.domain.store;

import com.example.deliveryadmin.common.embeded.Address;
import com.example.deliveryadmin.common.embeded.OpeningHours;

public class StoreValidate {
    /**
     * 매장 운영 시간
     * : NOT NULL, 모든 필드 값 입력 필수
     */
    public static void validAddress(Address address) {
        if (address == null || !address.isNotEmpty()) {
            throw new IllegalArgumentException("주소를 입력해 주세요.");
        }
    }


    /**
     * 매장 운영 시간
     * : NOT NULL, 모든 필드 값 입력 필수
     */
    public static void validOpeningHours(OpeningHours openingHours) {
        if (openingHours == null || !openingHours.isBusinessHoursSet()) {
            throw new IllegalArgumentException("매장 운영 시간을 입력해 주세요.");
        }
    }


}
