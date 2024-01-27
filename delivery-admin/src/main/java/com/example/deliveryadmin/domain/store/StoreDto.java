package com.example.deliveryadmin.domain.store;

import com.example.deliveryadmin.common.embeded.Address;
import com.example.deliveryadmin.common.embeded.OpeningHours;
import com.example.deliveryadmin.common.enums.StoreCategory;
import com.example.deliveryadmin.common.enums.StoreStatus;
import com.example.deliveryadmin.common.fileupload.AttachmentFile;
import com.example.deliveryadmin.common.fileupload.AttachmentFileDto;
import com.example.deliveryadmin.domain.member.Member;
import com.example.deliveryadmin.domain.member.MemberDto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.querydsl.core.annotations.QueryProjection;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class StoreDto {
    // === Request(역직렬화) === //

    /**
     * 전체 목록 조회
     * 필터링 대상 : 카테고리, 운영 상태, 매장명
     */
    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class RequestSearchDto {
        private StoreCategory category;
        private StoreStatus status;
        private String name;

        public RequestSearchDto(StoreCategory category, StoreStatus status, String name) {
            this.category = category;
            this.status = status;
            this.name = name;
        }
    }


    /**
     * 신규 매장 등록
     */
    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class RequestSaveDto {
        @NotBlank(message = "매장명을 입력해주세요.")
        @Size(max = 20)
        private String name;

        private MemberDto.DetailInfo member;

        private String description;

        @NotNull(message = "주소는 필수 입니다.")
        private Address address;

        @NotNull(message = "매장 운영 시간을 입력해주세요.")
        private OpeningHours openingHours;

        @NotNull(message = "매장 카테고리를 선택해주세요.")
        private StoreCategory category;

        @NotNull(message = "매장 운영 상태를 선택해주세요.")
        private StoreStatus status;

        @Builder
        public RequestSaveDto(String name, MemberDto.DetailInfo member, String description, Address address, OpeningHours openingHours, StoreCategory category, StoreStatus status) {
            this.name = name;
            this.member = member;
            this.description = description;
            this.address = address;
            this.openingHours = openingHours;
            this.category = category;
            this.status = status;
        }


        public void setMember(Member member) {
            this.member = new MemberDto.DetailInfo(member);
        }

    }

    /**
     * 매장 정보 수정
     */
    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @Builder
    public static class RequestUpdateDto {
        @NotBlank(message = "매장명을 입력해주세요.")
        @Size(max = 20)
        private String name;

        private String description;

        @NotNull(message = "주소는 필수 입니다.")
        private Address address;

        @NotNull(message = "매장 운영 시간을 입력해주세요.")
        private OpeningHours openingHours;

        @NotNull(message = "매장 운영 상태를 선택해주세요.")
        private StoreStatus status;

        @Builder(toBuilder = true)
        public RequestUpdateDto(String name, String description, Address address, OpeningHours openingHours, StoreStatus status) {
            this.name = name;
            this.description = description;
            this.address = address;
            this.openingHours = openingHours;
            this.status = status;
        }


    }


    // === Response(직렬화) === //

    @Getter
    public static class DetailInfo {
        private Long id;

        private String name;

        private String description;

        private Address address;

        private OpeningHours openingHours;

        private StoreCategory category;

        private StoreStatus status;

        private MemberDto.DetailInfo member;

        private List<AttachmentFileDto> thumbnails = new ArrayList<>();

        public DetailInfo() {
        }

        @QueryProjection
        @Builder
        public DetailInfo(Long id, String name, String description, Address address, OpeningHours openingHours, StoreCategory category, StoreStatus status, MemberDto.DetailInfo member, List<AttachmentFileDto> thumbnails) {
            this.id = id;
            this.name = name;
            this.description = description;
            this.address = address;
            this.openingHours = openingHours;
            this.category = category;
            this.status = status;
            this.member = member;
            this.thumbnails = thumbnails;
        }


        // Store 엔티티 입력 시 DTO로 변환 - 파일 업로드 시 사용
        @QueryProjection
        @Builder
        // Entity -> Dto
        public DetailInfo(Store store) {
            this.id = store.getId();
            this.name = store.getName();
            this.description = store.getDescription();
            this.address = store.getAddress();
            this.openingHours = store.getOpeningHours();
            this.category = store.getCategory();
            this.status = store.getStatus();
            this.member = new MemberDto.DetailInfo(store.getMember());


            if (store.getThumbnails() != null) {
                this.thumbnails = AttachmentFileDto.convertAttachmentFilesToDto(store.getThumbnails());
            }
        }



    }
}
