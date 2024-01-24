package com.example.deliveryadmin.domain.store.repository;

import com.example.deliveryadmin.domain.store.Store;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreRepository extends JpaRepository<Store, Long>, StoreRepositoryCustom {
    Store findOneById(Long storeId);

    boolean existsById(Long storeId);

    void deleteById(Long storeId);
}
