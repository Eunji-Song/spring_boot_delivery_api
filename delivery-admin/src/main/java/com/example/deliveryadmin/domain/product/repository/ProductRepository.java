package com.example.deliveryadmin.domain.product.repository;

import com.example.deliverycore.entity.Product;
import com.example.deliveryadmin.domain.product.ProductDto;
import com.example.deliverycore.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long>, ProductRepositoryCustom {
    boolean existsProductByIdAndName(Long storeId, String name);

    Optional<Product> findByIdAndStoreId(Long productId, Long storeId);

    boolean existsProductById(Long productId);

}
