package com.example.deliveryadmin.domain.store.repository;

import com.example.deliveryadmin.common.fileupload.dto.QStoreAttachmentFileDto_DetailImages;
import com.example.deliveryadmin.common.fileupload.dto.StoreAttachmentFileDto;
import com.example.deliverycore.entity.QMember;
import com.example.deliverycore.entity.QStore;
import com.example.deliverycore.entity.Store;
import com.example.deliverycore.entity.attachmentfile.QStoreAttachmentFile;
import com.example.deliverycore.entity.attachmentfile.StoreAttachmentFile;
import com.example.deliverycore.enums.StoreCategory;
import com.example.deliverycore.enums.StoreStatus;
import com.example.deliveryadmin.domain.store.*;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.ComparableExpressionBase;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
public class StoreRepositoryImpl implements StoreRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    private QStore store;
    private QMember member;
    private QStoreAttachmentFile storeAttachmentFile;

    private OrderSpecifier<?>[] getSort(Sort sort) {
        log.info("sort : {}", sort);

        return sort.stream()
                .map(order -> {
                    ComparableExpressionBase<?> comparableExpressionBase = Expressions.comparablePath(Comparable.class, store, order.getProperty());
                    return order.isAscending() ? comparableExpressionBase.asc() : comparableExpressionBase.desc();
                })
                .toArray(OrderSpecifier[]::new);
    }



    @Override
    public Page<StoreDto.ListViewData> findStore(StoreCategory storeCategory, StoreStatus storeStatus, String name, Pageable pageable) {
        store = new QStore("s");

        // WHERE 문 생성
        BooleanBuilder booleanBuilder = new BooleanBuilder();

        booleanBuilder.and(store.isDel.isFalse());

        if (storeCategory != null) {
            booleanBuilder.and(store.category.eq(storeCategory));
        }

        if (storeStatus != null) {
            booleanBuilder.and(store.status.eq(storeStatus));
        }

        if (name != null) {
            booleanBuilder.and(store.name.contains(name));
        }

        QueryResults<StoreDto.ListViewData> results = jpaQueryFactory
                                            .select(new QStoreDto_ListViewData(store.id, store.name, store.address, store.category, store.status, store.thumbnail))
                                            .from(store)
                                            .where(booleanBuilder)
                                            .orderBy(getSort(pageable.getSort()))
                                            .offset(pageable.getOffset())
                                            .limit(pageable.getPageSize())
                                            .fetchResults();

        List<StoreDto.ListViewData> content = results.getResults();
        long total = results.getTotal();

        return new PageImpl<>(content, pageable, total);
    }

    @Override
    public boolean isExistStoreName(Long adminId, String name, Long storeId) {
        store = new QStore("s");

        // 동적 쿼리 생성
        BooleanBuilder booleanBuilder = new BooleanBuilder();


        booleanBuilder.and(store.isDel.eq(false)).and(store.admin.id.eq(adminId)).and(store.name.eq(name));

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
    public boolean isExists(Long storeId) {
        store = new QStore("s");

        return jpaQueryFactory
                .selectOne()
                .from(store)
                .where(store.isDel.isFalse(), store.id.eq(storeId))
                .fetchOne() != null;
    }

    @Override
    public StoreDto.DetailInfo findOneById(Long storeId) {
        log.info("[StoreRepositoryImpl:findOneById] 게시글 상세 조회");

        store = new QStore("store");
        storeAttachmentFile = new QStoreAttachmentFile("saf");

        // 기본 정보 + 썸네일
        StoreDto.DetailInfo detailInfo = jpaQueryFactory.select(new QStoreDto_DetailInfo(store))
                .from(store)
                .where(store.isDel.isFalse(), store.id.eq(storeId))
                .fetchOne();

        // 상세 이미지
        List<StoreAttachmentFileDto.DetailImages> detailImages = new ArrayList<>();
        if (detailInfo != null) {
            detailImages = jpaQueryFactory.select(new QStoreAttachmentFileDto_DetailImages(storeAttachmentFile))
                    .from(storeAttachmentFile)
                    .where(storeAttachmentFile.store.id.eq(storeId), storeAttachmentFile.isDel.isFalse(), storeAttachmentFile.isDetailImage.isTrue())
                    .fetch();
        }


        // 상세 이미지 조회
        return new StoreDto.DetailInfo(detailInfo, detailImages);
    }

    @Override
    public Store findOneByIdToEntity(Long storeId) {
        log.info("[StoreRepositoryImpl:findOneByIdToEntity] 게시글 상세 조회 리턴 타입 : Entity");

        store = new QStore("s");
        storeAttachmentFile = new QStoreAttachmentFile("saf");

        // 기본 정보 + 썸네일
        Store entity = jpaQueryFactory.select(store)
                .from(store)
                .leftJoin(store.thumbnail, storeAttachmentFile).fetchJoin()
                .where(store.isDel.isFalse(), store.id.eq(storeId))
                .fetchOne();

        if (entity != null) {
            // 상세 이미지 리턴
            List<StoreAttachmentFile> detailImages = jpaQueryFactory.select(storeAttachmentFile)
                    .from(storeAttachmentFile)
                    .where(storeAttachmentFile.store.id.eq(storeId), storeAttachmentFile.isDel.isFalse(), storeAttachmentFile.isDetailImage.isTrue())
                    .fetch();
            entity.setDetailImages(detailImages);
        }

        return entity;
    }


    @Override
    public void deleteById(Long storeId) {
        log.info("[StoreRepositoryImpl:deleteById] 게시글 삭제 처리 ");

        store = new QStore("s");
        storeAttachmentFile = new QStoreAttachmentFile("saf");


        jpaQueryFactory
                .update(store)
                .set(store.isDel, true)
                .where(store.id.eq(storeId))
                .execute();

        jpaQueryFactory
                .update(storeAttachmentFile)
                .set(storeAttachmentFile.isDel, true)
                .where(storeAttachmentFile.store.id.eq(storeId))
                .execute();

    }
}
