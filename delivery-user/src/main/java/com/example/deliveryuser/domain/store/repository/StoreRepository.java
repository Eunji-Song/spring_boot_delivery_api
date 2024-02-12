package com.example.deliveryuser.domain.store.repository;

import com.example.deliverycore.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StoreRepository extends JpaRepository<Store, Long>, StoreRepositoryCustom {
    Store getStoreById(Long storeId);
}
