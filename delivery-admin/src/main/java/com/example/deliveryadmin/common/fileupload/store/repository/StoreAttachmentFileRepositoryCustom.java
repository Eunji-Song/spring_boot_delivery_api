package com.example.deliveryadmin.common.fileupload.store.repository;

import java.util.List;

public interface StoreAttachmentFileRepositoryCustom {
    void deleteFileById(Long attachmentId);

    void deleteFilesByIdList(List<Integer> deleteFilesIdList);
}
