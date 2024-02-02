//package com.example.deliveryadmin.domain.delivery;
//
//import com.example.deliverycore.embeded.Address;
//import com.example.deliveryadmin.common.entity.BaseEntity;
//import com.example.deliverycore.enums.OrderStatus;
//import com.example.deliveryadmin.domain.order.Order;
//import com.fasterxml.jackson.annotation.JsonIgnore;
//import jakarta.persistence.*;
//import lombok.AccessLevel;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//
//@Entity
//@Getter
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
//public class Delivery extends BaseEntity  {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "delivery_id")
//    private Long id;
//
//    // order_id
//    @JsonIgnore
//    @OneToOne(fetch = FetchType.LAZY)
//    private Order order;
//
//    // order status
//    @Enumerated(value = EnumType.STRING)
//    private OrderStatus orderStatus;
//
//    // 주소
//    private Address address;
//}
