package com.example.deliveryuser.domain.order.repository;

import com.example.deliverycore.entity.Order;
import com.example.deliverycore.enums.OrderStatus;
import com.example.deliveryuser.domain.order.OrderDto;
import org.aspectj.weaver.ast.Or;

public interface OrderRepositoryCustom {
    Order findPendingOrderById(Long orderId);

    OrderDto.OrderDetail findOrderById(Long orderId);
}
