package com.example.deliveryadmin.domain.store.repository;

import com.example.deliverycore.entity.Product;
import com.example.deliveryadmin.domain.product.ProductDto;
import com.example.deliverycore.entity.Store;
import com.example.deliveryadmin.domain.store.StoreDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StoreRepository extends JpaRepository<Store, Long>, StoreRepositoryCustom {
    void deleteById(Long storeId);

}
