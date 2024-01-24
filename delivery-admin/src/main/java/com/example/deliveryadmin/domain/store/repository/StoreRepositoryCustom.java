package com.example.deliveryadmin.domain.store.repository;

import com.example.deliveryadmin.domain.store.StoreDto;

import java.util.List;

public interface StoreRepositoryCustom {
    List<StoreDto.DetailInfo> findAllStore(StoreDto.RequestSearchDto requestSearchDto);


    boolean isExistStoreName(Long adminId, String name);
}
