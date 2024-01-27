package com.example.deliveryadmin.domain.store.repository;

import com.example.deliveryadmin.common.fileupload.dto.*;
import com.example.deliveryadmin.common.fileupload.entity.AttachmentFile;
import com.example.deliveryadmin.common.fileupload.entity.QAttachmentFile;
import com.example.deliveryadmin.common.fileupload.entity.QStoreAttachmentFile;
import com.example.deliveryadmin.domain.member.QMember;
import com.example.deliveryadmin.domain.store.QStore;
import com.example.deliveryadmin.domain.store.QStoreDto_DetailInfo;
import com.example.deliveryadmin.domain.store.Store;
import com.example.deliveryadmin.domain.store.StoreDto;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
public class StoreRepositoryImpl implements StoreRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

//    // 로컬 변수를 멤버 변수로 변경
    private QStore store;
    private QMember member;
    private QAttachmentFile attachmentFile;
    private QStoreAttachmentFile storeAttachmentFile;


    @Override
    public List<StoreDto.DetailInfo> findStore(StoreDto.RequestSearchDto requestSearchDto) {
        return null;
    }

    @Override
    public boolean isExistStoreName(Long adminId, String name, Long storeId) {
        store = new QStore("s");

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
        member = new QMember("member");


        // store, member 데이터 호출
        log.info("[StoreRepositoryImpl:findOneById] 매장, 사용자 정보 조회");

        StoreDto.DetailInfo storeAndMemberInfo = jpaQueryFactory.select(new QStoreDto_DetailInfo(store, member))
                .from(store)
                .leftJoin(store.member, member).fetchJoin()
                .where(store.id.eq(storeId), store.isDel.isFalse())
                .fetchOne();


        // == 파일 관련 코드 == //
        attachmentFile = new QAttachmentFile("a");
        storeAttachmentFile = new QStoreAttachmentFile("b");


        // 썸네일 호출
        log.info("[StoreRepositoryImpl:findOneById] 썸네일 조회");

        StoreAttachmentFileDto.Thumbnail thumbnail = jpaQueryFactory
                .select(new QStoreAttachmentFileDto_Thumbnail(storeAttachmentFile, attachmentFile))
                .from(storeAttachmentFile)
                .join(storeAttachmentFile.attachmentFile, attachmentFile).fetchJoin()
                .where(storeAttachmentFile.isThumbnail.isTrue(), storeAttachmentFile.store.id.eq(storeAndMemberInfo.getId()), attachmentFile.isDel.isFalse(), storeAttachmentFile.isDel.isFalse())
                .fetchOne();


        // 상세 이미지
        log.info("[StoreRepositoryImpl:findOneById] 상세 이미지 조회 ");

        List<StoreAttachmentFileDto.DetailImages> detailImages = jpaQueryFactory.select(new QStoreAttachmentFileDto_DetailImages(storeAttachmentFile, attachmentFile))
                .from(storeAttachmentFile)
                .join(storeAttachmentFile.attachmentFile, attachmentFile).fetchJoin()
                .where(storeAttachmentFile.isDetailImage.isTrue(), storeAttachmentFile.store.id.eq(storeAndMemberInfo.getId()), attachmentFile.isDel.isFalse(), storeAttachmentFile.isDel.isFalse())
                .fetch();


        return new StoreDto.DetailInfo(storeAndMemberInfo, thumbnail, detailImages);
    }

    @Override
    public Store findOneByIdToEntity(Long storeId) {
        log.info("[StoreRepositoryImpl:findOneByIdToEntity] 게시글 상세 조회 리턴 타입 : Entity");

        store = new QStore("s");

        Store storeEntity = jpaQueryFactory.select(store)
                .from(store)
                .where(store.isDel.isFalse(), store.id.eq(storeId))
                .fetchFirst();
        return storeEntity;
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
