package com.example.deliveryadmin.domain.store;

import com.example.deliveryadmin.common.embeded.Address;
import com.example.deliveryadmin.common.embeded.OpeningHours;
import com.example.deliveryadmin.common.enums.StoreCategory;
import com.example.deliveryadmin.common.enums.StoreStatus;
import jakarta.persistence.Embedded;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;


public class StoreRequestDto {
    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @ToString
    public static class SaveDto {
        @NotBlank(message = "매장명을 입력해주세요.")
        @Size(max = 20)
        private String name;

        private Long adminId;

        private String description;

        @Embedded
        @NotNull(message = "주소는 필수 입니다.")
        private Address address;

        @Embedded
        @NotNull(message = "매장 운영 시간을 입력해주세요.")
        private OpeningHours openingHours;

        @Enumerated(EnumType.STRING)
        @NotNull(message = "매장 카테고리를 선택해주세요.")
        private StoreCategory category;

        @Enumerated(EnumType.STRING)
        @NotNull(message = "매장 운영 상태를 선택해주세요.")
        private StoreStatus status;

        @Builder(toBuilder = true)
        public SaveDto(String name, Long adminId, String description, Address address, OpeningHours openingHours, StoreCategory category, StoreStatus status) {
            this.name = name;
            this.adminId = adminId;
            this.description = description;
            this.address = address;
            this.openingHours = openingHours;
            this.category = category;
            this.status = status;
        }

        public void setAdminId(Long adminId) {
            this.adminId = adminId;
        }
    }
}
