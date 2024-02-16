package com.example.deliveryuser.domain.cart.repository;

import com.example.deliverycore.entity.Cart;
import com.example.deliverycore.entity.QCart;
import com.example.deliverycore.entity.QCartItem;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class CartRepositoryImpl implements CartRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    private QCart cart;
    private QCartItem cartItem;

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

    @Override
    public void delete(Long cartId, Long memberId) {
        // cartItem
        cartItem = new QCartItem("ci");
        jpaQueryFactory.delete(cartItem).where(cartItem.cart.id.eq(cartId)).execute();

        // cart
        cart = new QCart("c");
        jpaQueryFactory.delete(cart).where(cart.id.eq(cartId), cart.member.id.eq(memberId)).execute();

    }
}
