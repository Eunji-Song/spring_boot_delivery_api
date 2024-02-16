package com.example.deliveryuser.domain.cart;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

public class CartDto {
    // === Request === //

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class RequestCartDto {
        @NotNull(message = "매장 ID를 입력해주세요.")
        private Long storeId;

        @NotNull(message = "상품 ID를 입력해주세요.")
        private Long productId;

        @Min(value = 1, message = "상품 수량은 최소 1 이상이어야 합니다.")
        private int quantity;

        private Long memberId;

        @Builder(toBuilder = true)
        public RequestCartDto(Long storeId, Long productId, int quantity) {
            this.storeId = storeId;
            this.productId = productId;
            this.quantity = quantity;
        }


    }

    // === Response === //

    /**
    * 장바구니 > 상품
     */
    public static class CartProduct {
        private Long productId;
        private String productName;
        private int productPrice;
        private int quantity;

        private int totalPrice;
    }

    /**
     * 장바구니
     */
    public static class CartList {

        // 가게 정보
        private Long storeId;
        private String storeName;

        // 메뉴 목록
        List<CartDto.CartProduct> productList = new ArrayList<>();


        // 가격 합계
        private int totalPrice;
    }


}
