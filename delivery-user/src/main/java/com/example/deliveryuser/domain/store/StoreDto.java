package com.example.deliveryuser.domain.store;

import com.example.deliverycore.embeded.Address;
import com.example.deliverycore.entity.attachmentfile.StoreAttachmentFile;
import com.example.deliverycore.enums.StoreCategory;
import com.example.deliverycore.enums.StoreStatus;
import com.example.deliveryuser.common.attachment.dto.AttachmentFileDto;
import com.example.deliveryuser.common.attachment.dto.StoreAttachmentFileDto;
import com.querydsl.core.annotations.QueryProjection;
import lombok.*;

import java.util.ArrayList;
import java.util.List;


public class StoreDto {
    // === Request(역직렬화) === //

    /**
     * 전체 목록 조회
     * 필터링 대상 : 카테고리, 운영 상태, 매장명
     */
    @ToString
    @Getter
    public static class RequestSearchDto {
        private StoreCategory category;
        private String name;

        public RequestSearchDto(StoreCategory category, String name) {
            this.category = category;
            this.name = name;
        }

        public RequestSearchDto() {
        }
    }


    // === Response(직렬화) === //

    /**
     * 목록 조회
     */

    @Getter
    public static class ListViewData {
        private Long id;

        private String name;

        private int minOrderAmount;

        // 평점

        // Best 메뉴

        private StoreAttachmentFileDto.Thumbnail thumbnail;

        public ListViewData() {
        }

        @QueryProjection
        public ListViewData(Long id, String name, int minOrderAmount, StoreAttachmentFileDto.Thumbnail thumbnail) {
            this.id = id;
            this.name = name;
            this.minOrderAmount = minOrderAmount;
            this.thumbnail = thumbnail;
        }

        @QueryProjection
        public ListViewData(Long id, String name, int minOrderAmount, StoreAttachmentFile thumbnail) {
            this.id = id;
            this.name = name;
            this.minOrderAmount = minOrderAmount;
            this.thumbnail = new StoreAttachmentFileDto.Thumbnail(thumbnail);
        }
    }


    /**
     * 상세 조회
     */
    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class DetailViewData {
        private Long id;

        private String name;

        private Address address;

        private int minOrderAmount;

        private List<AttachmentFileDto> detailImages = new ArrayList<>();


        @QueryProjection
        public DetailViewData(Long id, String name, Address address, int minOrderAmount) {
            this.id = id;
            this.name = name;
            this.address = address;
            this.minOrderAmount = minOrderAmount;
            this.detailImages = new ArrayList<>();
        }

        public void setDetailImages(List<AttachmentFileDto> detailImages) {
            this.detailImages = detailImages;
        }
    }
}
