package com.example.deliveryadmin.domain.store;

import com.example.deliveryadmin.common.embeded.Address;
import com.example.deliveryadmin.common.embeded.OpeningHours;
import com.example.deliveryadmin.common.enums.StoreStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

public class StoreResponseDto {
    /**
     * 매장 상세 조회 응답
     */
    public static class StoreDetailDTO {
        private Long id;

        private String name;

        private String description;

        private Address address;

        private OpeningHours openingHours;

        private StoreStatus status;
    }

    public static class SaveDto {

    }

}
