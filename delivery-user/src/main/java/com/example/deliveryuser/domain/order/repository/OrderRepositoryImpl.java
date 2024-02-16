package com.example.deliveryuser.domain.order.repository;

import com.example.deliverycore.entity.Order;
import com.example.deliverycore.entity.QOrder;
import com.example.deliverycore.enums.OrderStatus;
import com.example.deliveryuser.domain.order.OrderDto;
import com.example.deliveryuser.domain.order.QOrderDto_OrderDetail;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class OrderRepositoryImpl implements OrderRepositoryCustom{
    private final JPAQueryFactory jpaQueryFactory;
    private QOrder order;

    @Override
    public Order findPendingOrderById(Long orderId) {
        order = new QOrder("o");

        Order orderEntity = jpaQueryFactory.select(order).from(order).where(order.id.eq(orderId), order.orderStatus.eq(OrderStatus.PENDING)).fetchOne();
        return orderEntity;
    }

    @Override
    public OrderDto.OrderDetail findOrderById(Long orderId) {
        order = new QOrder("o");

        OrderDto.OrderDetail orderDetail = jpaQueryFactory.select(new QOrderDto_OrderDetail(order.id, order.orderStatus))
                .from(order)
                .where(order.id.eq(orderId))
                .fetchOne();
        return orderDetail;
    }
}
