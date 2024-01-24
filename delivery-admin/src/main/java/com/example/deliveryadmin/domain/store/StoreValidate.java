package com.example.deliveryadmin.domain.store;

import com.example.deliveryadmin.common.embeded.Address;
import com.example.deliveryadmin.common.embeded.OpeningHours;

public class StoreValidate {
    private static boolean isNull(Object... objects) {
        for (Object obj : objects) {
            if (obj == null) {
                return true;
            }
        }
        return false;
    }

    /**
     * 매장 운영 시간
     * : NOT NULL, 모든 필드 값 입력 필수
     */
    public static void validAddress(Address address) {
        if (isNull(address, address.getCity(), address.getDetail(), address.getStreet(), address.getZipCode())) {
            throw new IllegalArgumentException("주소를 입력해 주세요.");
        }
    }


    /**
     * 매장 운영 시간
     * : NOT NULL, 모든 필드 값 입력 필수
     */
    public static void validOpeningHours(OpeningHours openingHours) {
        if (isNull(openingHours, openingHours.getOpenTime(), openingHours.getCloseTime())) {
            throw new IllegalArgumentException("매장 운영 시간을 입력해 주세요.");
        }
    }
}
