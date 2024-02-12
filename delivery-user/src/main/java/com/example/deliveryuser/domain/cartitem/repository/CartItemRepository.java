package com.example.deliveryuser.domain.cartitem.repository;

import com.example.deliverycore.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    void deleteById(Long cartId);
}
