package com.example.deliveryuser.domain.order;

import com.example.deliverycore.enums.OrderStatus;
import com.querydsl.core.annotations.QueryProjection;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class OrderDto {

    /**
     * 상세 조회
     */
    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class OrderDetail {
        private Long orderId;
        private OrderStatus orderStatus;

        @QueryProjection
        public OrderDetail(Long orderId, OrderStatus orderStatus) {
            this.orderId = orderId;
            this.orderStatus = orderStatus;
        }
    }
}
