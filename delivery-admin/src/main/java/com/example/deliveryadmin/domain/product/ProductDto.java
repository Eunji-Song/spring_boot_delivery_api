package com.example.deliveryadmin.domain.product;

import com.example.deliveryadmin.common.enums.ProductCategory;
import com.example.deliveryadmin.common.fileupload.product.ProductAttachmentFile;
import com.example.deliveryadmin.common.fileupload.product.ProductAttachmentFileDto;
import com.example.deliveryadmin.domain.member.Member;
import com.example.deliveryadmin.domain.store.Store;
import com.querydsl.core.annotations.QueryProjection;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.validator.constraints.Range;

public class ProductDto {
    // === Request(역직렬화) === //

    /**
     * 전체 목록 조회
     */
    @Getter
    @NoArgsConstructor(access =  AccessLevel.PROTECTED)
    public static class RequestSearch {
        private ProductCategory productCategory;
        private String name;
    }


    /**
     * 신규 매장 등록
     */

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class RequestSave {
        private ProductCategory category;

        @NotBlank(message = "메뉴명을 입력해 주세요.")
        private String name;

        private String description;

        @NotNull(message = "가격을 입력해 주세요.")
        @Digits(integer = 10, fraction = 0, message = "가격은 소수점을 포함하지 않는 정수로 입력되어야 합니다.")
        @Min(value = 1000)
        private int price;

        private boolean isBest;

        @NotNull(message = "매장 정보를 입력해주세요.")
        private Long storeId;


        private Member member;

        private ProductAttachmentFile thumbnail;

        @Builder(toBuilder = true)
        public RequestSave(ProductCategory category, String name, String description, int price, boolean isBest, Long storeId, Member member, ProductAttachmentFile thumbnail) {
            this.category = category;
            this.name = name;
            this.description = description;
            this.price = price;
            this.isBest = isBest;
            this.storeId = storeId;
            this.member = member;
            this.thumbnail = thumbnail;
        }

        public void setMember(Member member) {
            this.member = member;
        }

        public void setThumbnail(ProductAttachmentFile thumbnail) {
            this.thumbnail = thumbnail;
        }

    }

    /**
     * 매장 정보 수정
     */

    @ToString
    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class RequestUpdate {
        private ProductCategory category;

        @NotBlank(message = "메뉴명을 입력해 주세요.")
        private String name;

        private String description;

        @NotNull(message = "가격을 입력해 주세요.")
        @Digits(integer = 10, fraction = 0, message = "가격은 소수점을 포함하지 않는 정수로 입력되어야 합니다.")
        @Min(value = 1000)
        private int price;

        private Boolean isBest;

        private ProductAttachmentFile thumbnail;

        @Builder(toBuilder = true)
        public RequestUpdate(ProductCategory category, String name, String description, int price, boolean isBest, ProductAttachmentFile thumbnail) {
            this.category = category;
            this.name = name;
            this.description = description;
            this.price = price;
            this.isBest = isBest;
            this.thumbnail = thumbnail;
        }


        public void setThumbnail(ProductAttachmentFile thumbnail) {
            this.thumbnail = thumbnail;
        }

    }


    // === Response(직렬화) === //

    /**
     * 메뉴 리스트 조회
     */
    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class ListData {
        private Long id;
        private String name;
        private String description;
        private ProductAttachmentFileDto thumbnail;
        private boolean isBest;

        @QueryProjection
        public ListData(Long id, String name, String description, ProductAttachmentFile thumbnail, boolean isBest) {
            this.id = id;
            this.name = name;
            this.description = description;
//            this.thumbnail = new ProductAttachmentFileDto(thumbnail);
            this.thumbnail = null;
            this.isBest = isBest;
        }
    }

    /**
     * 메뉴 상세 조회
     */
    public static class DetailInfo {

    }


}
