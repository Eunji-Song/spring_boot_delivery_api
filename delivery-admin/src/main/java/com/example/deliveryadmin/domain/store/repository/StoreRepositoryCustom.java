package com.example.deliveryadmin.domain.store.repository;

import com.example.deliveryadmin.common.enums.StoreCategory;
import com.example.deliveryadmin.common.enums.StoreStatus;
import com.example.deliveryadmin.domain.store.Store;
import com.example.deliveryadmin.domain.store.StoreDto;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface StoreRepositoryCustom {
    List<StoreDto.ListViewData> findStore(StoreCategory storeCategory, StoreStatus status, String name, Pageable pageable);

    boolean isExists(Long storeId);

    boolean isExistStoreName(Long adminId, String name, Long storeId);

    StoreDto.DetailInfo findOneById(Long storeId);

    Store findOneByIdToEntity(Long storeId);

    void deleteById(Long storeId);
}
