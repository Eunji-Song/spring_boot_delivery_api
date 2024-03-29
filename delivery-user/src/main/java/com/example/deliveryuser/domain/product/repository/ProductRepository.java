package com.example.deliveryuser.domain.product.repository;

import com.example.deliverycore.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long>, ProductRepositoryCustom {
    boolean existsByIdAndStoreId(Long productId, Long storeId);
}
