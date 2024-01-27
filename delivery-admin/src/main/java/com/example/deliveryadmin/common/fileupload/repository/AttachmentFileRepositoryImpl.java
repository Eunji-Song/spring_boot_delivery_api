package com.example.deliveryadmin.common.fileupload.repository;

import com.example.deliveryadmin.common.fileupload.QAttachmentFile;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class AttachmentFileRepositoryImpl implements AttachmentFileRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public void deleteAttachmentFileInfo(Long attachmentFileId) {
        QAttachmentFile attachmentFile = new QAttachmentFile("f");

        jpaQueryFactory
                .update(attachmentFile)
                .set(attachmentFile.isDel, true)
                .where(attachmentFile.id.eq(attachmentFileId))
                .execute();
    }
}
