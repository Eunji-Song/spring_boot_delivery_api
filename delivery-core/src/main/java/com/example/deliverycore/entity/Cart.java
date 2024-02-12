package com.example.deliverycore.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Cart extends BaseEntity  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private Store store;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL)
    private List<CartItem> items = new ArrayList<>();

    // 주문 금액 총액
    private int totalPrice;

    public Cart() {
    }

    @Builder(toBuilder = true)
    public Cart(Store store, Member member, int totalPrice) {
        this.store = store;
        this.member = member;
        this.totalPrice = totalPrice;
    }

    public void setTotalPrice() {
        int price = 0;

        if (items.size() > 0) {
            for (CartItem item : items) {
                price += item.getTotalPrice();
            }
        }


        this.totalPrice = price;

    }
}
