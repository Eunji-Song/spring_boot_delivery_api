package com.example.deliveryadmin.domain.store.repository;
import com.example.deliveryadmin.domain.store.QStore;
import com.example.deliveryadmin.domain.store.QStoreDto_DetailInfo;
import com.example.deliveryadmin.domain.store.Store;
import com.example.deliveryadmin.domain.store.StoreDto;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
public class StoreRepositoryImpl implements StoreRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<StoreDto.DetailInfo> findAllStore(StoreDto.RequestSearchDto requestSearchDto) {
        return null;
    }

    @Override
    public boolean isExistStoreName(Long adminId, String name, Long storeId) {
        QStore store = new QStore("s");

        // 동적 쿼리 생성하기
        BooleanBuilder booleanBuilder = new BooleanBuilder();

        // 기본 조건 필드
        booleanBuilder.and(store.isDel.eq(false)).and(store.member.id.eq(adminId)).and(store.name.eq(name));


        if (storeId != null) {
            booleanBuilder.and(store.id.ne(storeId));
        }


        return jpaQueryFactory
                .selectOne()
                .from(store)
                .where(booleanBuilder)
                .fetchOne() != null;
    }

    @Override
    public StoreDto.DetailInfo findOneById(Long storeId) {
        log.info("[StoreRepositoryImpl:findOneById] 게시글 상세 조회");
        QStore store = new QStore("s");


        StoreDto.DetailInfo detailInfo = jpaQueryFactory.select(new QStoreDto_DetailInfo(store))
                                                    .from(store)
                                                    .where(store.isDel.isFalse(), store.id.eq(storeId))
                                                    .fetchFirst();
        log.info("detail");
        log.info(detailInfo.getThumbnails().toString());

        return detailInfo;
    }

    @Override
    public Store findOneByIdToEntity(Long storeId) {
        log.info("[StoreRepositoryImpl:findOneByIdToEntity] 게시글 상세 조회 리턴 타입 : Entity");

        QStore store = new QStore("s");

        Store storeEntity = jpaQueryFactory.select(store)
                .from(store)
                .where(store.isDel.isFalse(), store.id.eq(storeId))
                .fetchFirst();
        return storeEntity;
    }
}
