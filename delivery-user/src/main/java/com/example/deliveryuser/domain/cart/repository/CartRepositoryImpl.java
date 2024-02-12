package com.example.deliveryuser.domain.cart.repository;

import com.example.deliverycore.entity.Cart;
import com.example.deliverycore.entity.QCart;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class CartRepositoryImpl implements CartRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    private QCart cart;

    @Override
    public boolean isExistAnotherStoreCart(Long memberId, Long storeId) {
        cart = new QCart("c");

        boolean isExist = jpaQueryFactory.selectOne()
                .from(cart)
                .where(cart.member.id.eq(memberId), cart.store.id.ne(storeId))
                .fetchFirst() != null;
        return isExist;
    }

    @Override
    public Cart getCart(Long memberId) {
        cart = new QCart("c");

        return jpaQueryFactory.select(cart)
                .from(cart)
                .where(cart.member.id.eq(memberId))
                .fetchFirst();
    }
}
