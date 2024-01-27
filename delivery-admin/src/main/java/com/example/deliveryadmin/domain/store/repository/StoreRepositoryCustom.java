package com.example.deliveryadmin.domain.store.repository;

import com.example.deliveryadmin.domain.store.Store;
import com.example.deliveryadmin.domain.store.StoreDto;

import java.util.List;
import java.util.Optional;

public interface StoreRepositoryCustom {
    List<StoreDto.DetailInfo> findAllStore(StoreDto.RequestSearchDto requestSearchDto);


    boolean isExistStoreName(Long adminId, String name, Long storeId);

    StoreDto.DetailInfo findOneById(Long storeId);

    Store findOneByIdToEntity(Long storeId);
}
