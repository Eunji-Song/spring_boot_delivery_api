package com.example.deliveryuser.domain.cart.repository;

import com.example.deliverycore.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long>, CartRepositoryCustom  {
    boolean existsByIdAndMemberId(Long cartId, Long memberId);

    Cart findCartById(Long cartId);

}
