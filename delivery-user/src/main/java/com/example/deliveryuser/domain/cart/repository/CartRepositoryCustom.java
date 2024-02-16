package com.example.deliveryuser.domain.cart.repository;

import com.example.deliverycore.entity.Cart;

public interface CartRepositoryCustom {
    boolean isExistAnotherStoreCart(Long memberId, Long storeId);

    Cart getCart(Long memberId);

    void delete(Long cartId, Long memberId);
}
