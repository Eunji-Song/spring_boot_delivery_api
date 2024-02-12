package com.example.deliveryuser.domain.product;

import com.example.deliverycore.entity.attachmentfile.ProductAttachmentFile;
import com.example.deliverycore.enums.ProductCategory;
import com.example.deliveryuser.common.attachment.dto.ProductAttachmentFileDto;
import com.querydsl.core.annotations.QueryProjection;
import lombok.*;

public class ProductDto {
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
        private ProductAttachmentFileDto thumbnail = null;
        private boolean isBest;

        @QueryProjection
        public ListData(Long id, String name, String description, ProductAttachmentFile thumbnail, boolean isBest) {
            this.id = id;
            this.name = name;
            this.description = description;
            this.thumbnail = thumbnail != null ? new ProductAttachmentFileDto(thumbnail) : null;
            this.isBest = isBest;
        }
    }

    /**
     * 메뉴 상세 조회
     */
    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class DetailInfo {
        private Long id;
        private String name;
        private String description;
        private int price;
        private ProductAttachmentFileDto thumbnail = null;
        private boolean isBest;

        @QueryProjection
        public DetailInfo(Long id,String name, String description, int price, ProductAttachmentFile thumbnail, boolean isBest) {
            this.id = id;
            this.name = name;
            this.description = description;
            this.price = price;
            this.thumbnail = thumbnail != null ? new ProductAttachmentFileDto(thumbnail) : null;
            this.isBest = isBest;
        }
    }


}
