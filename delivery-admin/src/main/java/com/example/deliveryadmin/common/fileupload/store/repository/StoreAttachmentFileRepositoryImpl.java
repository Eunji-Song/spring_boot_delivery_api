package com.example.deliveryadmin.common.fileupload.store.repository;

import com.example.deliverycore.entity.attachmentfile.QStoreAttachmentFile;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class StoreAttachmentFileRepositoryImpl implements StoreAttachmentFileRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    private QStoreAttachmentFile storeAttachmentFile;

    @Override
    public void deleteFileById(Long attachmentId) {
        storeAttachmentFile = new QStoreAttachmentFile("s");

        jpaQueryFactory
                .update(storeAttachmentFile)
                .set(storeAttachmentFile.isDel, true)
                .where(storeAttachmentFile.id.eq(attachmentId))
                .execute();
    }

    @Override
    public void deleteFilesByIdList(List<Integer>deleteFilesIdList) {
        int listSize = deleteFilesIdList.size();
        Integer arr[] = deleteFilesIdList.toArray(deleteFilesIdList.toArray(new Integer[listSize]));


        storeAttachmentFile = new QStoreAttachmentFile("s");
        jpaQueryFactory
                .update(storeAttachmentFile)
                .set(storeAttachmentFile.isDel, true)
                .where(storeAttachmentFile.id.in(arr))
                .execute();

    }
}
