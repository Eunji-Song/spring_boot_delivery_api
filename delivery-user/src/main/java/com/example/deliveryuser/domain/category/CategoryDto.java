package com.example.deliveryuser.domain.category;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class CategoryDto {
    // === Request(역직렬화) === //


    // === Response(직렬화) === //

    /**
     * 전체 목록 조회
     */
    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class ListData {
        private Long id;
        private String name;
        private String value;


        @Builder
        public ListData(Long id, String name, String value) {
            this.id = id;
            this.name = name;
            this.value = value;
        }
    }
}
