package com.example.deliveryadmin.domain.order;

import com.example.deliverycore.entity.Order;
import com.example.deliverycore.entity.Product;
import com.example.deliverycore.entity.Store;

public class OrderDto {
    // Request

    // Response
    public static class ListViewData {
        private Long orderId;
        private Long storeId;
        private Order order;
    }
}
