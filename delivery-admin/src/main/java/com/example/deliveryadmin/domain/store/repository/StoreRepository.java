package com.example.deliveryadmin.domain.store.repository;

import com.example.deliveryadmin.domain.store.Store;
import com.example.deliveryadmin.domain.store.StoreDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreRepository extends JpaRepository<Store, Long>, StoreRepositoryCustom {
//    Page<StoreDto.ListViewData> findAll(Pageable pageable);

    void deleteById(Long storeId);

}
