package com.example.deliverycore.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Table(name = "orders_item")
@ToString
// 주문 상품
public class OrderItem extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    private int quantity; // 전체 주문 수량

    private int totalPrice;


    public OrderItem() {
    }

    public OrderItem(Order order, CartItem cartItem) {
        this.order = order;
        this.product = cartItem.getProduct();
        this.quantity = cartItem.getQuantity();
        this.totalPrice = cartItem.getTotalPrice();
    }
}
