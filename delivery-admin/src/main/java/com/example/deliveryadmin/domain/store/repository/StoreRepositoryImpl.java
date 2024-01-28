package com.example.deliveryadmin.domain.store.repository;

import com.example.deliveryadmin.common.enums.StoreCategory;
import com.example.deliveryadmin.common.enums.StoreStatus;
import com.example.deliveryadmin.common.exception.store.StoreNotFoundException;
import com.example.deliveryadmin.common.fileupload.attachment.QAttachmentFile;
import com.example.deliveryadmin.common.fileupload.store.*;
import com.example.deliveryadmin.domain.member.QMember;
import com.example.deliveryadmin.domain.store.*;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
public class StoreRepositoryImpl implements StoreRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    private QStore store;
    private QMember member;
    private QStoreAttachmentFile storeAttachmentFile;


    @Override
    public List<StoreDto.ListViewData> findStore(StoreCategory storeCategory, StoreStatus storeStatus, String name, Pageable pageable) {
        store = new QStore("s");

        // WHERE 문 생성
        BooleanBuilder booleanBuilder = new BooleanBuilder();

        if (storeCategory != null) {
            booleanBuilder.and(store.category.eq(storeCategory));
        }

        if (storeStatus != null) {
            booleanBuilder.and(store.status.eq(storeStatus));
        }

        if (name != null) {
            booleanBuilder.and(store.name.contains(name));
        }

        log.info("list query");
        List<StoreDto.ListViewData> list = jpaQueryFactory.select(new QStoreDto_ListViewData(store.id, store.name, store.address, store.category, store.status, store.thumbnail)).from(store).where(booleanBuilder).fetch();


//        jpaQueryFactory.select(store)


//        jpaQueryFactory.select(QStoreDto_ListViewData(store.id, store.name, store.address, store.category, store.status))


        return list;


    }

    @Override
    public boolean isExistStoreName(Long adminId, String name, Long storeId) {
        store = new QStore("s");

        // 동적 쿼리 생성
        BooleanBuilder booleanBuilder = new BooleanBuilder();


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
    public boolean isExists(Long storeId) {
        log.info("[StoreRepositoryImpl:isExists] 가게 중복 여부 확인");
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
        member = new QMember("member");


        // store, member 데이터 호출
        log.info("[StoreRepositoryImpl:findOneById] 매장, 사용자 정보 조회");

        StoreDto.DetailInfo storeAndMemberInfo = jpaQueryFactory.select(new QStoreDto_DetailInfo(store, member))
                .from(store)
                .leftJoin(store.member, member).fetchJoin()
                .where(store.id.eq(storeId), store.isDel.isFalse())
                .fetchOne();


        // 매장, 사용자 정보가 존재하지 않는 경우 Notfound 발생(필수)
        if (storeAndMemberInfo == null) {
            throw new StoreNotFoundException();
        }


        // == 파일 관련 코드 == //
        storeAttachmentFile = new QStoreAttachmentFile("b");


        // 썸네일 호출
        log.info("[StoreRepositoryImpl:findOneById] 썸네일 조회");

        StoreAttachmentFileDto.Thumbnail thumbnail = jpaQueryFactory
                .select(new QStoreAttachmentFileDto_Thumbnail(storeAttachmentFile))
                .from(storeAttachmentFile)
                .where(storeAttachmentFile.isThumbnail.isTrue(), storeAttachmentFile.store.id.eq(storeAndMemberInfo.getId()), storeAttachmentFile.isDel.isFalse())
                .fetchOne();


        // 상세 이미지
        log.info("[StoreRepositoryImpl:findOneById] 상세 이미지 조회 ");

        List<StoreAttachmentFileDto.DetailImages> detailImages = jpaQueryFactory.select(new QStoreAttachmentFileDto_DetailImages(storeAttachmentFile))
                .from(storeAttachmentFile)
                .where(storeAttachmentFile.isDetailImage.isTrue(), storeAttachmentFile.store.id.eq(storeAndMemberInfo.getId()), storeAttachmentFile.isDel.isFalse())
                .fetch();

        return new StoreDto.DetailInfo(storeAndMemberInfo, thumbnail, detailImages);

    }

    @Override
    public Store findOneByIdToEntity(Long storeId) {
        log.info("[StoreRepositoryImpl:findOneByIdToEntity] 게시글 상세 조회 리턴 타입 : Entity");

        store = new QStore("s");
        storeAttachmentFile = new QStoreAttachmentFile("saf");

        // 기본 정보 리턴
//        jpaQueryFactory.select()
//                .from(store)
//                .where(store.isDel.isFalse(), store.id.eq(storeId))
//                .fetchFirst();

//        log.info("entity : {}", storeEntity.getDetailImages());

//        log.info("thumbnail도 갸져와 ");
//        jpaQueryFactory.select(Projections.fields(store.id, store.thumbnail))
//                .from(store)
//                .where(store.isDel.isFalse(), store.id.eq(storeId))
//                .fetch();


        // 썸네일 정보 리턴
//        log.info("썸네일 정보 리턴");
//        storeAttachmentFile = new QStoreAttachmentFile("saf");
//        Long thumbnailId = jpaQueryFactory.select(storeAttachmentFile.id)
//                .from(storeAttachmentFile)
//                .where(storeAttachmentFile.isDel.isFalse(), storeAttachmentFile.store.id.eq(storeId), storeAttachmentFile.isThumbnail.isTrue())
//                .fetchFirst();
//
//        Store store1 = storeEntity.toBuilder().thumbnail()

//        Store store1 = storeEntity.toBuilder().thumbnailId(thumbnailId).build();
        log.info("==== Fin ====");


        Store store1 = jpaQueryFactory.select(store)
                .from(store)
                .fetchFirst();


        return store1;
    }


    @Override
    public void deleteById(Long storeId) {
        log.info("[StoreRepositoryImpl:deleteById] 게시글 삭제 처리 ");
        store = new QStore("s");

        jpaQueryFactory
                .update(store)
                .set(store.isDel, true)
                .where(store.id.eq(storeId))
                .execute();

        QStoreAttachmentFile storeAttachmentFile = new QStoreAttachmentFile("sa");
        jpaQueryFactory
                .update(storeAttachmentFile)
                .set(storeAttachmentFile.isDel, true)
                .where(storeAttachmentFile.store.id.eq(storeId))
                .execute();

    }
}
