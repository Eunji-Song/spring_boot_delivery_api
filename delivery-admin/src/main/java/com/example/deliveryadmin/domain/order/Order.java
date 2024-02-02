//package com.example.deliveryadmin.domain.order;
//
//import com.example.deliveryadmin.common.entity.BaseEntity;
//import com.example.deliverycore.enums.OrderStatus;
//import com.example.deliverycore.entity.Store;
//import jakarta.persistence.*;
//import lombok.AccessLevel;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import org.hibernate.annotations.ColumnDefault;
//
//@Entity
//@Getter
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
//@Table(name = "orders")
//public class Order extends BaseEntity {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "order_id")
//    private Long id;
//
//    // 주문자
//    @Column(nullable = false)
//    private Long user_id;
//
//    // 주문 매장
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "store_id")
//    private Store store;
//
//    // 주문 처리 상태
//    @Column(nullable = false)
//    @ColumnDefault(value = "'PENDING'")
//    @Enumerated(value = EnumType.STRING)
//    private OrderStatus orderStatus;
//
//    // 주문 금액 총액
//    private int totalPrice;
//
//}
