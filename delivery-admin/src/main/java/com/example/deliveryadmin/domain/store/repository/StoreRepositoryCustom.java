package com.example.deliveryadmin.domain.store.repository;

import com.example.deliverycore.enums.StoreCategory;
import com.example.deliverycore.enums.StoreStatus;
import com.example.deliverycore.entity.Store;
import com.example.deliveryadmin.domain.store.StoreDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface StoreRepositoryCustom {
    Page<StoreDto.ListViewData> findStore(StoreCategory storeCategory, StoreStatus status, String name, Pageable pageable);

    boolean isExists(Long storeId);

    boolean isExistStoreName(Long adminId, String name, Long storeId);

    StoreDto.DetailInfo findOneById(Long storeId);

    Store findOneByIdToEntity(Long storeId);

    void deleteById(Long storeId);
}
