package com.example.deliveryadmin.domain.store.repository;

import com.example.deliveryadmin.domain.store.QStore;
//import com.example.deliveryadmin.domain.store.Store;
//import com.example.deliveryadmin.domain.store.dto.QStoreResponseDto_DetailDto;
//import com.example.deliveryadmin.domain.store.dto.StoreDetailDto;
import com.example.deliveryadmin.domain.store.StoreDto;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
public class StoreRepositoryImpl implements StoreRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<StoreDto.DetailInfo> findAllStore(StoreDto.RequestSearchDto requestSearchDto) {
        return null;
    }

    @Override
    public boolean isExistStoreName(Long adminId, String name) {
        QStore store = new QStore("s");
        return jpaQueryFactory
                .selectOne()
                .from(store)
                .where(store.isDel.eq(false), store.member.id.eq(adminId), store.name.eq(name))
                .fetchOne() != null;
    }
}
