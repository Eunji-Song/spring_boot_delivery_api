package com.example.deliveryadmin.domain.product.repository;

import com.example.deliveryadmin.domain.product.Product;
import com.example.deliveryadmin.domain.product.ProductDto;
import com.example.deliveryadmin.domain.store.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long>, ProductRepositoryCustom {
    boolean existsProductByIdAndName(Long storeId, String name);

    Optional<Product> findById(Long productId);

    boolean existsProductById(Long productId);
}
