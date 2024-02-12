package com.example.deliveryuser.domain.store.repository;

import com.example.deliverycore.enums.StoreCategory;
import com.example.deliveryuser.domain.store.StoreDto;

import java.util.List;

public interface StoreRepositoryCustom {
    List<StoreDto.ListViewData> findAllStores(StoreCategory storeCategory, String name);

    StoreDto.DetailViewData findStoreById(Long storeId);
}
