package com.example.deliveryuser.domain.store.repository;

import com.example.deliverycore.entity.QStore;
import com.example.deliverycore.entity.attachmentfile.QStoreAttachmentFile;
import com.example.deliverycore.entity.attachmentfile.StoreAttachmentFile;
import com.example.deliverycore.enums.StoreCategory;
import com.example.deliveryuser.common.attachment.dto.AttachmentFileDto;
import com.example.deliveryuser.common.attachment.dto.QStoreAttachmentFileDto_DetailImages;
import com.example.deliveryuser.common.attachment.dto.StoreAttachmentFileDto;
import com.example.deliveryuser.domain.store.QStoreDto_DetailViewData;
import com.example.deliveryuser.domain.store.QStoreDto_ListViewData;
import com.example.deliveryuser.domain.store.StoreDto;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
public class StoreRepositoryImpl implements StoreRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;
    private QStore store = new QStore("s");
    private QStoreAttachmentFile storeAttachmentFile = new QStoreAttachmentFile("saf");

    @Override
    public List<StoreDto.ListViewData> findAllStores(StoreCategory storeCategory, String name) {
        BooleanBuilder booleanBuilder = new BooleanBuilder();

        if (storeCategory != null) {
            booleanBuilder.and(store.category.eq(storeCategory));
        }

        if (name != null && !name.isBlank()) {
            booleanBuilder.and(store.name.eq(name));
        }

        List<StoreDto.ListViewData> list = jpaQueryFactory.select(new QStoreDto_ListViewData(store.id, store.name, store.minOrderAmount,store.thumbnail))
                .from(store)
                .where(booleanBuilder)
                .fetch();

        return list;
    }


    @Override
    public StoreDto.DetailViewData findStoreById(Long storeId) {
        StoreDto.DetailViewData detailViewData = jpaQueryFactory.select(new QStoreDto_DetailViewData(store.id, store.name, store.address, store.minOrderAmount)).from(store).where(store.id.eq(storeId)).fetchOne();

        // 상세 이미지
        if (detailViewData != null) {
            // 상세 이미지 리턴
            List<AttachmentFileDto> detailImages = jpaQueryFactory.select(Projections.constructor(AttachmentFileDto.class,storeAttachmentFile.fileInfo))
                    .from(storeAttachmentFile)
                    .where(storeAttachmentFile.store.id.eq(storeId), storeAttachmentFile.isDel.isFalse(), storeAttachmentFile.isDetailImage.isTrue())
                    .fetch();

            detailViewData.setDetailImages(detailImages);
        }
        return detailViewData;
    }
}
